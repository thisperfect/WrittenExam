package com.dayee.writtenExam.framework.util;

import java.beans.IndexedPropertyDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {

	/**
	 * Logger for this class
	 */
    private static final Logger logger = LoggerFactory
                                               .getLogger(PropertyUtils.class);
    
	public static void logProperties(Object obj) throws Exception {

		if (logger.isDebugEnabled()) {
			logger.debug("Class:" + obj.getClass());
			PropertyDescriptor[] pds = getPropertyDescriptors(obj);
			if (CollectionUtils.notEmpty(pds)) {
				for (PropertyDescriptor pd : pds) {
					logger.debug("Property:" + pd.getName() + "[read="
							+ isReadable(pd) + ",write=" + isWriteable(pd)
							+ "]="
							+ PropertyUtils.getProperty(obj, pd.getName()));
				}
			}
		}
		
	}

	public static boolean isBeanProperty(PropertyDescriptor desc) {

		return isReadable(desc) && isWriteable(desc);

	}

	public static boolean isReadable(PropertyDescriptor desc) {

		if (desc == null) {
			throw new IllegalArgumentException("No desc specified");
		}
		Method readMethod = desc.getReadMethod();
		if (readMethod == null && (desc instanceof IndexedPropertyDescriptor)) {
			readMethod = ((IndexedPropertyDescriptor) desc)
					.getIndexedReadMethod();
		}
		return readMethod != null;

	}

	public static boolean isWriteable(PropertyDescriptor desc) {

		if (desc == null) {
			throw new IllegalArgumentException("No desc specified");
		}
		Method writeMethod = desc.getWriteMethod();
		if (writeMethod == null && (desc instanceof IndexedPropertyDescriptor)) {
			writeMethod = ((IndexedPropertyDescriptor) desc)
					.getIndexedWriteMethod();
		}
		return writeMethod != null;

	}

	public static void setPropertyNotRewrite(Object bean, String name,
			Object value) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		if (PropertyUtils.isWriteable(bean, name)) {
			Object existValue = PropertyUtils.getProperty(bean, name);
			if (existValue == null) {
				if (value != null && DataTypeUtils.isString(value)) {
					value = ((String) value).trim();
				}

				org.apache.commons.beanutils.PropertyUtils.setProperty(bean,
						name, value);
			}
		}

	}

	public static void setProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		if (PropertyUtils.isWriteable(bean, name)) {
			if (value != null && DataTypeUtils.isString(value)) {
				value = ((String) value).trim();
			}

			org.apache.commons.beanutils.PropertyUtils.setProperty(bean, name,
					value);
		}

	}

	public static Object createByExample(Class<?> dest, Object orig)
			throws Exception {

		if (orig == null) {
			return null;
		}
		Object obj = dest.newInstance();
		copyProperties(obj, orig);
		return obj;
	}

	public static boolean copyProperties(Object dest, Object orig,
			boolean isIgnoreNull) throws Exception {

	    boolean isChange = false;
		Assert.notNull(dest);
		Assert.notNull(orig);
		PropertyDescriptor[] pds = getPropertyDescriptors(dest.getClass());

		for (PropertyDescriptor pd : pds) {

			String propertyName = pd.getName();

			if (isReadable(dest, propertyName)
					&& isWriteable(dest, propertyName)
					&& isReadable(orig, propertyName)) {

				Object value = getProperty(orig, propertyName);
				Object destValue = getProperty(dest,propertyName);
				// = pd.getReadMethod().invoke(orig, new Object[] {});
				if (value != null || !isIgnoreNull) {
					if (value != null && DataTypeUtils.isString(value)) {
						String str = (String) value;
						if (StringUtils.hasLength(str)) {
							value = str.trim();
						}
					}
					pd.getWriteMethod().invoke(dest, value);
				}
				
				if(value!=null&&!"forceAdd".equals(propertyName)&&!"changeLog".equals(propertyName)){
				   if(!DataTypeUtils.isList(value)){
    				   if(!value.equals(destValue)){
    				       //内容发生变更
    				       isChange = true;
    				   }
				   }
				}

			}
		}
		return isChange;

	}
	
	public static void copyProperties(Object dest, Object orig,
			boolean isIgnoreNull,String ignoreFields) throws Exception {

		Assert.notNull(dest);
		Assert.notNull(orig);
		PropertyDescriptor[] pds = getPropertyDescriptors(dest.getClass());

		if(StringUtils.isNotBlank(ignoreFields)){
			ignoreFields=ignoreFields.replaceAll(",","|");
		}
		
		for (PropertyDescriptor pd : pds) {

			String propertyName = pd.getName();
			if(StringUtils.isNotBlank(ignoreFields)&&propertyName.matches(ignoreFields)){
				continue;
			}

			if (isReadable(dest, propertyName)
					&& isWriteable(dest, propertyName)
					&& isReadable(orig, propertyName)) {

				Object value = getProperty(orig, propertyName);
				if (value != null || !isIgnoreNull) {
					if (value != null && DataTypeUtils.isString(value)) {
						String str = (String) value;
						if (StringUtils.hasLength(str)) {
							value = str.trim();
						}
					}
					pd.getWriteMethod().invoke(dest, value);
				}
			}
		}
	}

	public static void copyProperty(Object dest, Object orig,
			String propertyName) throws Exception {

		if (isReadable(dest, propertyName) && isWriteable(dest, propertyName)
				&& isReadable(orig, propertyName)) {
			PropertyDescriptor pd = getPropertyDescriptor(dest, propertyName);
			Object value = pd.getReadMethod().invoke(orig, new Object[0]);

			if (value != null && DataTypeUtils.isString(value)) {
				String str = (String) value;
				if (StringUtils.hasLength(str)) {
					value = str.trim();
				}
			}

			pd.getWriteMethod().invoke(dest, value);
		}

	}

	public static void copyPropertiesForSchame(Object dest, Object orig)
			throws Exception {

		copyPropertiesForSchame(dest, orig, true);
	}

	public static void copyPropertiesForSchame(Object dest, Object orig,
			boolean isIgnoreNull) throws Exception {

		Assert.notNull(dest);
		Assert.notNull(orig);
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(dest
				.getClass());

		for (PropertyDescriptor pd : pds) {

			String propertyName = pd.getName();

			if (PropertyUtils.isReadable(dest, propertyName)
					&& PropertyUtils.isWriteable(dest, propertyName)
					&& PropertyUtils.isReadable(orig, propertyName)) {

				Object value = PropertyUtils.getProperty(orig, propertyName);

				if (value != null || !isIgnoreNull) {
					// System.out.println("propertyName start: "+propertyName+",value: "+value);
					Class<?> propertyType = pd.getPropertyType();
					if (DataTypeUtils.isBigInteger(propertyType)) {
						if (value != null) {
							if (DataTypeUtils.isString(value)) {
								value = BigInteger.valueOf(Integer
										.valueOf((String) value));
							} else if (DataTypeUtils.isInteger(value)) {
								value = BigInteger.valueOf((Integer) value);
							}
						} else {
							value = (BigInteger) value;
						}
					} else if (DataTypeUtils.isInteger(propertyType)) {
						if (value != null) {
							if (DataTypeUtils.isString(value)) {
								value = Integer.valueOf((String) value);
							} else if (DataTypeUtils.isBigInteger(value)) {
								value = ((BigInteger) value).intValue();
							}
						} else {
							value = (Integer) value;
						}
					} else if (DataTypeUtils.isDate(propertyType)) {
						if (value != null) {
							if (DataTypeUtils.isString(value)) {
								value = DateUtils.parseDate((String) value);
							}
						} else {
							value = (Date) value;
						}
					} else // css add else
							// System.out.println("propertyName: "+propertyName+",value: "+value);
					if (DataTypeUtils.isString(value)) {
						String str = (String) value;
						if (StringUtils.hasLength(str)) {
							value = str.trim();
						}
					}

					pd.getWriteMethod().invoke(dest, value);
				}

			}
		}

	}

	public static void copyPropertiesIgnoreNull(Object dest, Object orig)
			throws Exception {

		copyProperties(dest, orig, true);

	}

	public static List<?> createListByExample(Class<?> dest, List<?> origList)
			throws Exception {

		if (CollectionUtils.isEmpty(origList)) {
			return null;
		}
		List<Object> result = new ArrayList<Object>(origList.size());
		for (Object obj : origList) {
			result.add(createByExample(dest, obj));
		}
		return result;
	}

	public static boolean isEqProperty(Object newProperty, Object oldProperty) {

		if (newProperty == null && oldProperty == null) {
			return true;
		}

		if (newProperty != null && oldProperty != null) {
			return newProperty.equals(oldProperty);
		}
		return false;
	}

	public static <T> Collection<T> getProperties(Collection<?> beans, String propertyName) {
		return getProperties(beans, propertyName, true, true);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Collection<T> getProperties(Collection<?> beans, String propertyName,
			boolean ignoreNullOrEmptyPropertyValue, boolean removeRepeatedValue) {
		Collection<T> values = removeRepeatedValue ? new LinkedHashSet<T>(beans.size())
				: new ArrayList<T>(beans.size());
		if (CollectionUtils.isNotEmpty(beans)) {
			for (Object bean : beans) {
				try {
					T propertyValue = (T) getProperty(bean, propertyName);
					if (ignoreNullOrEmptyPropertyValue) {
						if (propertyValue instanceof String) {
							if (StringUtils.isNotEmpty((String) propertyValue)) {
								values.add(propertyValue);
							}
						} else if (propertyValue != null) {
							values.add(propertyValue);
						}
					} else {
						values.add(propertyValue);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ArrayList<T>(0);
				}
			}
		}
		return values;
	}

}
