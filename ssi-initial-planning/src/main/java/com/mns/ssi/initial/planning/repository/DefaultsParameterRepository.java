package com.mns.ssi.initial.planning.repository;

import com.mns.ssi.initial.planning.entity.DefaultParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DefaultsParameterRepository extends CrudRepository<DefaultParameter, Long> {
    List<DefaultParameter> findByHierarchyIdIn(List<String> hierarchyIds, Pageable pageable);
    List<DefaultParameter> findByHierarchyIdIn(Set<String> hierarchyIds);
}
