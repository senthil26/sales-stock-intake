package com.mns.ssi.initial.planning.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.mns.ssi.initial.planning.controller.serialize.DefaultsHierarchySerializer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;
import java.util.stream.Collectors;

@JsonSerialize(using = DefaultsHierarchySerializer.class)
public final class DefaultsHierarchy {
    private static final String HEAD = "HEAD";

    private final SetMultimap<String, DefaultsNode> hierarchy;

    private DefaultsHierarchy(SetMultimap<String, DefaultsNode> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public static final DefaultsHierarchy newInstance() {
        return new DefaultsHierarchy(HashMultimap.create());
    }

    public void put(String id, DefaultsNode DefaultsNode) {
        hierarchy.put(id, DefaultsNode);
    }

    public Set<Map.Entry<String, DefaultsNode>> entries() {
        return new HashSet<>(hierarchy.entries());
    }

    public DefaultsHierarchy section(String key, int depth) {
        DefaultsNode head = hierarchy.values().stream()
                .filter(DefaultsNode -> DefaultsNode.getId().equals(key))
                .findAny().get();

        head.addChildren(traverse(head, depth));

        DefaultsHierarchy section = DefaultsHierarchy.newInstance();
        section.put(HEAD, head);
        return section;
    }

    public DefaultsHierarchy merge(DefaultsHierarchy other) {
        other.entries().stream().forEach(e -> this.put(e.getKey(), e.getValue()));
        this.head().stream().forEach(DefaultsNode -> this.put(HEAD, DefaultsNode));
        return this;
    }


    public Set<DefaultsNode> head() {
        return hierarchy.get(HEAD).stream().collect(Collectors.toSet());
    }

    public Collection<DefaultsNode> defaultNodes() {
        return hierarchy.values();
    }

    public Optional<DefaultsNode> findParent(String id, Level level) {
        return findDefaultsNode(id, level);
    }

    private Optional<DefaultsNode> findDefaultsNode(String id, Level level) {
        String key = hierarchy.entries().stream()
                .filter(e -> e.getValue().getId().equals(id))
                .filter(e -> !e.getKey().equals(id))
                .findFirst().get().getKey();

        if (hierarchy.get(key).stream().anyMatch(n -> n.getLevel().equals(level.name()))) {
            return hierarchy.get(key).stream().findAny();
        }

        return findDefaultsNode(key, level);
    }

    private Set<DefaultsNode> traverse(DefaultsNode defaultsNode, int depth) {
        if(depth <= 0 || defaultsNode.getLevel().equals(Level.ITEM.name())) {
            return Collections.emptySet();
        }

        String key = defaultsNode.getId();
        return hierarchy.get(key).stream()
                .filter(n -> n != null)
                .map(n -> {
                    Set<DefaultsNode> children = traverse(n, depth - 1);
                    n.addChildren(children);
                    return n;
                })
                .collect(Collectors.toSet());
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
