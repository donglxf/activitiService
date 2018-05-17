package com.ht.commonactivity.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * <p>扩展ObjectUtils</p> 
 *
 * @author: dyb
 * @since: 2018年1月4日上午10:40:38
 * @version: 1.0
 */
public abstract class ObjectUtils extends org.springframework.util.ObjectUtils {

	/**
	 * 
	 * <p>判断对象是否为空</p> 
	 * @param object
	 * @return 
	 * @throws
	 */
	public static boolean isEmpty(Object object) {
		if (object == null)
			return true;
		if (String.class.isAssignableFrom(object.getClass())) {
			return StringUtils.isBlank((String) object);
		}
		if (isArray(object)) {
			return (((Object[]) object).length == 0);
		}
		if (CollectionUtils.isCollection(object.getClass())) {
			return CollectionUtils.isEmpty((Collection<?>) object);
		}
		if (CollectionUtils.isMap(object.getClass())) {
			return CollectionUtils.isEmpty((Map<?, ?>) object);
		}
		return false;
	}

	/**
	 * 
	 * <p>转换对象</p> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <E> E transfer(Object value, E defaultValue) {
		E ret = defaultValue;
		if (null != value) {
			ret = (E) value;
		}
		return ret;
	}
	
	/**
	 * 为空时能得到默认非空值
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static <E> E getNotNull(E value, E defaultValue) {
		if (null == defaultValue) {
			throw new IllegalArgumentException("argument[defaultValue] cannot be null!");
		}
		E ret = defaultValue;
		if (null != value) {
			ret = value;
		}
		return ret;
	}
	
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}
}
