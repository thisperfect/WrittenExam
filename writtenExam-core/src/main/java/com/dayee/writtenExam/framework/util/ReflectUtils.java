
package com.dayee.writtenExam.framework.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

public class ReflectUtils {

    /**
     * 根据map值向实体中注入值
     * 
     * @param obj
     * @param map
     */
    public static void fillEntityByMap(Object obj, Map<String, String> map) {

        Set<Entry<String, String>> entrySet = map.entrySet();
        for (Entry<String, String> entry : entrySet) {
            setValue(obj, entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取一个类所有的属性（包括父类）
     * 
     * @param cls
     * @return
     */
    public static List<String> getAllField(Class cls) {

        List<String> list = new ArrayList<String>();
        fillField(cls, list);
        return list;
    }

    /**
     * 获取一个类的某个属性的值
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {

        Object object = null;
        PropertyDescriptor pd = null;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 读取属性
        try {
            object = pd.getReadMethod().invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void setValue(Object test,
                                String fieldName,
                                Object fieldValue) {

        PropertyDescriptor pd = null;
        try {
            pd = new PropertyDescriptor(fieldName, test.getClass());
        } catch (Exception e) {
            return;
        }
        // 写入属性
        try {
            Class cls = pd.getPropertyType();
            if (cls.toString().equals("class java.lang.Integer")) {
                pd.getWriteMethod()
                        .invoke(test, "".equals(fieldValue) ? -1 : Integer
                                .valueOf(fieldValue.toString()));
            } else if (cls.toString().equals("class java.lang.String")) {
                pd.getWriteMethod().invoke(test, (String) fieldValue);
            } else if (cls.toString().equals("boolean")) {
                pd.getWriteMethod().invoke(test, (boolean) fieldValue);
            } // 暂时只需要这两种数据类型，后续有需要再添加相应判断
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fillField(Class cls, List<String> list) {

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")
                || field.getName().equals("children")
                || field.getName().equals("createTime")
                || field.getName().equals("modifyTime")) {
                continue;
            }
            list.add(field.getName());
        }
        if (!cls.getSuperclass().toString().equals("class java.lang.Object")) {
            fillField(cls.getSuperclass(), list);
        }
    }

    public static Map<String, String> findAllStringField(Object obj)
            throws Exception {

        Map<String, String> strMap = new HashMap<String, String>();
        fillField(obj, strMap);
        return strMap;
    }

    private static void fillField(Object obj, Map<String, String> map)
            throws Exception {

        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            if (whetherString(field.getName(), cls)) {
                field.setAccessible(true);
                String fieldValue = (String) field.get(obj);
                if (fieldValue == null) {
                    continue;
                }
                map.put(field.getName(), fieldValue);
            }
        }
        if (!cls.getSuperclass().toString().equals("class java.lang.Object")) {
            fillField(cls.getSuperclass(), map);
        }
    }

    private static boolean whetherString(String fieldName, Class cls)
            throws Exception {

        PropertyDescriptor pd = new PropertyDescriptor(fieldName, cls);
        Class fieldCls = pd.getPropertyType();
        if (fieldCls.toString().equals("class java.lang.String")) {
            return true;
        }
        return false;
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz
     *            The class to introspect
     * @return the first generic declaration, or Object.class if cannot be
     *         determined
     */
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {

        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * @param clazz
     *            clazz The class to introspect
     * @param index
     *            the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     *         determined
     */
    public static Class getSuperClassGenricType(final Class clazz,
                                                final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];
    }
}
