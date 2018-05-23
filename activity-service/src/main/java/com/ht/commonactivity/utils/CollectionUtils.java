package com.ht.commonactivity.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.*;


/**
 * <p>集合工具类</p> 
 *
 * @author: dyb
 * @since: 2018年1月4日上午11:50:12
 * @version: 1.0
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {
	
	/**
	 * 从shortMap中获取property的值
	 * @param shortMap map
	 * @param property 属性
	 * @return
	 */
	public static final String getShortName(Map<String, String> shortMap,
			String property) {
		if (isEmpty(shortMap))
			return property;
		String temp = shortMap.get(property);
		if (StringUtils.isBlank(temp))
			return property;
		else
			return temp;
	}
	
	/**
	 * 判断对象是否是集合类型
	 * @param object
	 * @return
	 */
	public static final boolean isCollection(Object object) {
		Assert.notNull(object, "object must be not null!");
		return isCollection(object.getClass());
	}

	/**
	 * 判断对象是否是map类型
	 * @param object
	 * @return
	 */
	public static final boolean isMap(Object object) {
		Assert.notNull(object, "object must be not null!");
		return isMap(object.getClass());
	}

	/**
	 * 判断指定的clazz是否是集合类型
	 * @param clazz 指定的class
	 * @return
	 */
	public static final boolean isCollection(Class<?> clazz) {
		return Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * 判断指定的clazz是否是map类型
	 * @param clazz 指定的class
	 * @return
	 */
	public static final boolean isMap(Class<?> clazz) {
		return Map.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 将 Enumeration 类型转换成集合类型
	 * @param enums
	 * @return
	 */
	public static Collection<? extends Object> toCollection(
			Enumeration<? extends Object> enums) {
		Collection<Object> retColl = new ArrayList<Object>();
		while (enums.hasMoreElements()) {
			Object value = enums.nextElement();
			retColl.add(value);
		}
		return retColl;
	}

	/**
	 * 将 Enumeration 类型转换成List类型
	 * @param enums
	 * @return
	 */
	public static List<? extends Object> toList(
			Enumeration<? extends Object> enums) {
		List<Object> retList = new ArrayList<Object>();
		while (enums.hasMoreElements()) {
			Object value = enums.nextElement();
			retList.add(value);
		}
		return retList;
	}

	/**
	 * 将 Enumeration 类型转换成Set类型
	 * @param enums
	 * @return
	 */
	public static Set<? extends Object> toSet(Enumeration<? extends Object> enums) {
		Set<Object> retSet = new HashSet<Object>();
		while (enums.hasMoreElements()) {
			Object value = enums.nextElement();
			retSet.add(value);
		}
		return retSet;
	}
	
	/**
	 * return anyone in collection
	 * 
	 * @param collection
	 * @return
	 */
	public static final <E> E getAnyone(final Collection<E> collection) {
		if (!isEmpty(collection)) {
			return collection.iterator().next();
		}
		return null;
	}
	
	
	/**
	 * 移除map中值为空或值类型为指定类型type的k-v对
	 * @param map
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> Map<String, T> removeEmpty(final Map<String, ?> map,
			final Class<T> type) {
		Map<String, T> retMap = new HashMap<>();
		for (String key : map.keySet()) {
			T value = (T) map.get(key);
			if (!ObjectUtils.isEmpty(value)) {
				if (null == type || type.isAssignableFrom(value.getClass())) {
					retMap.put(key, value);
				}
			}
		}
		return retMap;
	}

	/**
	 * 移除map中值为空的k-v对
	 * @param map
	 * @return
	 */
	public static final <T> Map<String, T> removeEmpty(final Map<String, ?> map) {
		return removeEmpty(map, null);
	}
	
	
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}
	
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}
	
}
