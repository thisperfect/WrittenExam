
package com.dayee.writtenExam.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.dayee.writtenExam.model.BaseEntity;

public class BaseEntityUtils {

    /**
     * @param entityList
     * @return 返回map，以uuid做为key,对象做为返回值
     */
    public static <E extends Map<String, ?>> E convertToMapByUUID(Collection<?> entityList) {

        Map<String, BaseEntity> map = new HashMap<String, BaseEntity>();
        if (entityList == null) {
            return (E) map;
        }
        for (Object entity : entityList) {
            map.put(((BaseEntity) entity).getId(), (BaseEntity) entity);
        }
        return (E) map;
    }

    /**
     * @param entityList
     * @return 返回map，以propertyName做为key,对象做为返回值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <E extends Map<?, ?>> E convertToMapByProperty(Collection<?> entityList,
                                                                 String propertyName) {

        Map<Object, BaseEntity> map = new HashMap<Object, BaseEntity>();
        if (entityList == null) {
            return (E) map;
        }
        for (Object entity : entityList) {
            try {
                map.put(PropertyUtils.getProperty(entity, propertyName),
                        (BaseEntity) entity);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                continue;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }
        }
        return (E) map;
    }

    /**
     * @param entityList
     * @return 返回map，以keyPropertyName做为key,valuePropertyName做为value
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <E extends Map<?, ?>> E convertToMapByProperty(Collection<?> entityList,
                                                                 String keyPropertyName,
                                                                 String valuePropertyName) {

        Map<Object, Object> map = new HashMap<Object, Object>();
        if (entityList == null) {
            return (E) map;
        }
        for (Object entity : entityList) {
            try {
                map.put(PropertyUtils.getProperty(entity, keyPropertyName),
                        PropertyUtils.getProperty(entity, valuePropertyName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                continue;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                continue;
            }
        }
        return (E) map;
    }
}
