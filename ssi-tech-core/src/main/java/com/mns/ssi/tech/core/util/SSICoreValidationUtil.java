package com.mns.ssi.tech.core.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mns.ssi.tech.core.exception.SSICoreException;

public class SSICoreValidationUtil {
	
	/*private SSICoreValidationUtil() {

	}*/

	/**
	 * null check<br>
	 * 
	 * @param _obj
	 *            An object targeted for evaluation
	 * @return When the object is null, return true. Otherwise false.
	 */
	public static boolean isNull(Object _obj) {

		// When the object is other than null
		if (_obj != null) {
			return false;
		}
		return true;
	}

	/**
	 * not null check<br>
	 * 
	 * @param _obj
	 *            An object targeted for evaluation
	 * @return When the object is not null, return true. Otherwise false.
	 */
	public static boolean isNotNull(Object _obj) {

		// When the object is other than null
		if (_obj != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param o
	 *            Check whether an object is empty, will see if it is a String,
	 *            Map,Collection, etc.
	 * @return return if object is empty
	 */
	public static boolean isEmpty(Object o) {
		return isObjectEmpty(o);
	}

	/**
	 * @param o
	 *            Check whether an object is NOT empty, will see if it is a
	 *            String, Map, Collection, etc.
	 * @return return if object is NOT empty
	 */
	public static boolean isNotEmpty(Object _obj) {
		return !isObjectEmpty(_obj);
	}

	/**
	 * 
	 * @param o
	 *            Check whether string s is empty.
	 * @return return if the string is empty
	 */
	public static boolean isEmpty(String s) {
		return (s == null) || (s.isEmpty());
	}

	/**
	 * 
	 * @param c
	 *            Check whether collection c is empty
	 * @return return if the collection is empty
	 */
	public static <E> boolean isEmpty(Collection<E> _c) {
		return (_c == null) || _c.isEmpty();
	}

	/**
	 * 
	 * @param m
	 *            Check whether map m is empty
	 * @return return if the map is empty
	 */
	public static <K, E> boolean isEmpty(Map<K, E> m) {
		return (m == null) || (m.isEmpty());
	}

	/**
	 * 
	 * @param c
	 *            Check whether CharSequence c is empty
	 * @return return if the CharSequence is empty
	 */
	public static boolean isEmpty(CharSequence c) {
		return (c == null) || (c.length() == 0);
	}

	/**
	 * 
	 * @param s
	 *            Check whether string s is NOT empty.
	 * @return return if the string is NOT empty
	 */
	public static boolean isNotEmpty(String s) {
		return (s != null) && !s.isEmpty();
	}

	/**
	 * 
	 * @param c
	 *            Check whether Collection c is NOT empty
	 * @return return if the Collection is NOT empty
	 */
	public static <E> boolean isNotEmpty(Collection<E> c) {
		return (c != null) && !c.isEmpty();
	}

	/**
	 * 
	 * @param c
	 *            Check whether CharSequence c is NOT empty
	 * @return return if the CharSequence is NOT empty
	 */
	public static <E> boolean isNotEmpty(CharSequence c) {
		return (c != null) && (c.length() > 0);
	}

	/**
	 * 
	 * @param value
	 *            Check whether Object value is empty
	 * @return return if the Object is empty
	 */
	@SuppressWarnings("unchecked")
	private static boolean isCollectionEmpty(Object value) {
		if (value instanceof Collection) {
			return isEmpty((Collection<? extends Object>) value);
		} else {
			return isEmpty((Map<? extends Object, ? extends Object>) value);
		}

	}

	/**
	 * 
	 * @param value
	 *            Check whether Object value is empty
	 * @return return if the Object is empty
	 */
	public static boolean isObjectEmpty(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			return isEmpty((String) value);
		} else if (value instanceof CharSequence) {
			return isEmpty((CharSequence) value);
		} else if (value instanceof Collection || value instanceof Map) {
			return isCollectionEmpty(value);
		}
		return false;
	}

	public static void isEmpty(Object o, String message) throws SSICoreException {
		if (isObjectEmpty(o)) {
			throw new SSICoreException(message);
		}
	}

	public static void isValid(boolean condition, String message) throws SSICoreException {
		if (!condition) {
			throw new SSICoreException(message);
		}
	}

	/**
	 * Regular expression check<br>
	 * 
	 * @param _text
	 *            The character string targeted for evaluation
	 * @param _pattern
	 *            Regular expression pattern
	 * @return When matches with the regular expression, return true. Otherwise
	 *         false.
	 */
	public static boolean match(String _text, String _pattern) {

		// Check whether the specified character string matches with the regular
		// expression
		Pattern pattern = Pattern.compile(_pattern);
		Matcher matcher = pattern.matcher(_text);

		// result
		return matcher.matches();
	}

	/**
	 * Check the content <br>
	 * 
	 * @param _obj1
	 *            First object that has to be compared.
	 * @param _obj2
	 *            Next object that has to be compared.
	 * @return When 2 objects are same then true, otherwise false.
	 * @see Object#equals(Object)
	 */
	public static boolean isObjectsEqual(Object _obj1, Object _obj2) {

		if (isNull(_obj1) && isNull(_obj2)) {
			return true;
		}
		if (isNull(_obj1) && !isNull(_obj2)) {
			return false;
		}
		if (isNull(_obj2)) {
			return false;
		}
		return _obj1.equals(_obj2);
	}

	/**
	 * Returns true if s exist,else returns false
	 * 
	 * @param String
	 * @return
	 * 
	 */
	public static boolean exists(String s) {
		return s != null && s.trim().length() > 0;
	}

	/**
	 * <p>
	 * Indicates if a collection exists:
	 * </p>
	 * 
	 * returns true if c exists else returns false
	 * 
	 * @param Collection
	 * @return
	 * 
	 */
	public static boolean exists(Collection<?> c) {
		return c != null && !c.isEmpty();
	}

	/**
	 * <p>
	 * Indicates if a Map exists:
	 * </p>
	 * 
	 * returns true if m exists else returns false
	 * 
	 * @param Map
	 * @return
	 * 
	 */
	public static boolean exists(Map<?, ?> m) {
		return m != null && !m.isEmpty();
	}

	/**
	 * <p>
	 * Indicates if a Object exists:
	 * </p>
	 * 
	 * returns true if o exists else returns false
	 * 
	 * @param Object
	 *            array
	 * @return
	 * 
	 */
	public static boolean exists(Object[] o) {
		return o != null && o.length > 0;
	}

	/**
	 * <p>
	 * Indicates if a File exists:
	 * </p>
	 * 
	 * returns true if f exists else returns false
	 * 
	 * @param File
	 * @return
	 * 
	 */

	public static boolean exists(File f) {
		return f != null && f.exists();
	}

	/**
	 * <p>
	 * Indicates if an object exists.
	 * </p>
	 * 
	 * @param o
	 * @return true if exists else return false.
	 * @see #exists(Collection)
	 * @see #exists(File)
	 * @see #exists(Map)
	 * @see #exists(Object[])
	 * @see #exists(String)
	 */
	public static boolean exists(Object o) {
		if (o instanceof Collection)
			return exists((Collection<?>) o);
		if (o instanceof File)
			return exists((File) o);
		if (o instanceof Map)
			return exists((Map<?, ?>) o);
		if (o instanceof Object[])
			return exists((Object[]) o);
		if (o instanceof String)
			return exists((String) o);
		return o != null;
	}

	/**
	 * <p>
	 * Checks if the value matches the regular expression:
	 * </p>
	 * 
	 * @param regexp
	 *            The regular expression.
	 * 
	 * @return true if matches the regular expression.
	 */
	public static boolean matchRegexp(String value, String regexp) {
		if (regexp == null || regexp.length() <= 0) {
			return false;
		}

		return Pattern.matches(regexp, value);
	}

	public static boolean requiredFiled(String text) {
		if (text == null || text.length() <= 0 || text == "")
			return false;
		else
			return true;
	}

	/**
	 * Regular expression check<br>
	 * 
	 * @param _text
	 *            The character string targeted for evaluation
	 * @param _regex
	 *            The character string toward evaluation done
	 * @return When find special characters with the regular expression, return
	 *         true. Otherwise false.
	 */
	public static boolean checkForSpecialCharcters(String _text, String _regex) {
		if (isNull(_text) || isNull(_regex)) {
			return false;
		}
		Pattern pattern = Pattern.compile(_regex);
		Matcher matcher = pattern.matcher(_text);

		if (matcher.find() == true) {
			return true;
		}
		return false;
	}

	/**
	 * null check<br>
	 * 
	 * @param _obj
	 *            An object targeted for evaluation
	 * @return When the object is null, return true. Otherwise false.
	 */

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}

}
