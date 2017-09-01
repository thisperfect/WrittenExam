package com.dayee.writtenExam.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils<T> extends
        org.apache.commons.collections.CollectionUtils {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <E extends List> E splitList(List<?> list, int splitSize) {

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        int size = list.size();
        int fromIndex = 0;
        int toIndex = splitSize;
        List<List<?>> result = new ArrayList<List<?>>();
        if (size < splitSize) {
            result.add(list);
        } else {

            while (fromIndex < toIndex) {
                result.add(list.subList(fromIndex, toIndex));
                fromIndex = toIndex;
                toIndex += splitSize;
                if (toIndex > size) {
                    toIndex = size;
                }
            }
        }

        return (E) result;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?> splitSetToList(Set<?> set, int splitSize) {
        
        List<List> splitMultiList = new ArrayList<List>();
        List tempList = new ArrayList();
        splitMultiList.add(tempList);
        int count = 0;
        for(Object obj : set){
            if(count>=splitSize){
                count = 0;
                tempList = new ArrayList();
                splitMultiList.add(tempList);
            }
            tempList.add(obj);
            count++;
        }
        
        return splitMultiList;
    }
    

    public static boolean isEmpty(Object[] array) {

        return array == null || array.length == 0;
    }

    public static boolean notEmpty(Object[] array) {

        return array != null && array.length > 0;
    }

    public static boolean isEmpty(int[] array) {

        return array == null || array.length == 0;
    }

    public static boolean notEmpty(int[] array) {

        return array != null && array.length > 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {

        return map == null || map.size() == 0;
    }

    public static boolean notEmpty(Map<?, ?> map) {

        return map != null && map.size() > 0;
    }

    public static boolean notEmpty(Collection<?> coll) {

        return coll != null && coll.size() > 0;
    }

    public static boolean containsKey(Map<?, ?> map, Object key) {

        if (isEmpty(map)) {
            return false;
        }
        return map.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public static void addAll(Collection collection, Object elements[]) {

        if (collection != null && notEmpty(elements)) {
            int i = 0;
            for (int size = elements.length; i < size; i++) {
                if (DataTypeUtils.isString(elements[i])) {
                    String str = (String) elements[i];
                    if (str != null) {
                        str = str.trim();
                        if (StringUtils.hasLength(str)) {
                            collection.add(elements[i]);
                        }
                    }
                } else {
                    collection.add(elements[i]);
                }

            }
        }

    }

    @SuppressWarnings("unchecked")
    public static void arrayToList(Collection collection, Object elements[]) {

        if (collection != null && notEmpty(elements)) {
            int i = 0;
            for (int size = elements.length; i < size; i++) {
                if (DataTypeUtils.isString(elements[i])) {
                    if (StringUtils.hasLength((String) elements[i])) {
                        collection.add(elements[i]);
                    }
                } else {
                    collection.add(elements[i]);
                }

            }
        }

    }

    public static List<Integer> strToIntegerList(String str, String splitRegex) {

        List<Integer> ret = null;
        if (StringUtils.isNotEmpty(str)) {
            ret = new ArrayList<Integer>();
            String[] array = str.split(splitRegex);
            for (String s : array) {
                if (StringUtils.hasLength(s, true)) {
                    ret.add(Integer.parseInt(s.trim()));
                }
            }
        }
        return ret;

    }

    public static List<String> strToStrList(String str, String splitRegex) {

        List<String> ret = null;
        if (StringUtils.isNotEmpty(str)) {
            ret = new ArrayList<String>();
            String[] array = str.split(splitRegex);
            for (String s : array) {
                if (StringUtils.hasLength(s, true)) {
                    ret.add(s.trim());
                }
            }
        }
        return ret;
    }

    public static String integerListToStr(List<Integer> list, String joinString) {

        if (isNotEmpty(list)) {
            StringBuffer ret = null;
            ret = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                Integer integer = list.get(i);
                ret.append(String.valueOf(integer));
                if (i < list.size() - 1) {
                    ret.append(joinString);
                }
            }
            return ret.toString();
        } else {
            return null;
        }
    }

    public static <E> String collectionToStr(Collection<E> col,
            String joinString) {

        StringBuffer ret = new StringBuffer();
        if (isNotEmpty(col)) {
            boolean isFirst = true;
            Iterator<E> it = col.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                if (obj != null) {
                    if (!isFirst) {
                        ret.append(joinString);
                    }
                    ret.append(String.valueOf(obj));
                    isFirst = false;
                }
            }
        }
        return ret.toString();
    }

    @SuppressWarnings("unchecked")
    public static List<?> convertToList(Set<?> sets) {

        List list = new ArrayList();
        if (CollectionUtils.notEmpty(sets)) {
            for (Object obj : sets) {
                list.add(obj);
            }
        }
        return list;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E extends List> E copyList(List<?> fromList) {

        if (CollectionUtils.isNotEmpty(fromList)) {
            List<Object> toList = new ArrayList<Object>();
            for (Object from : fromList) {
                Object to = null;
                try {
                    to = from.getClass().newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }
                try {
                    PropertyUtils.copyPropertiesIgnoreNull(to, from);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                toList.add(to);
            }
            return (E) toList;
        }
        return null;
    }


    public static <T> Collection<T> filter(Collection<T> collection,
            String propertyName,
            Collection<? extends Object> containPropertyValueSet) {
        if (isEmpty(containPropertyValueSet)) {
            return collection;
        }
        List<T> resultList = new ArrayList<T>();
        if (isNotEmpty(collection)) {
            resultList = new ArrayList<T>(collection.size());
            try {
                for (T t : collection) {
                    if (containPropertyValueSet.contains(PropertyUtils
                            .getProperty(t, propertyName))) {
                        resultList.add(t);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }
    
 
    
    /**
     * 
     * @param values
     *            值集合。
     * @param prefix
     *            前缀。
     * @param suffix
     *            后缀。
     * @param sep
     *            分隔符。
     * @param ignoreNullOrEmpty
     *            是否忽略空值或空字符串。
     * @return
     */
    public static <T> String toString(Collection<T> values, String prefix, String suffix,
            String sep, boolean ignoreNullOrEmpty) {
        StringBuffer buffer = new StringBuffer();
        String s = prefix;
        buffer.append(s);
        if (ignoreNullOrEmpty) {
            s = StringUtils.EMPTY;
            boolean sepEmptyFlag = true;
            for (T value : values) {
                if (value instanceof String) {
                    if (StringUtils.isNotEmpty((String) value)) {
                        buffer.append(s).append(value);
                        s = sep;
                        sepEmptyFlag = false;
                    } else {
                        if (sepEmptyFlag) {
                            s = StringUtils.EMPTY;
                        } else {
                            s = sep;
                        }
                    }
                } else if (value != null) {
                    buffer.append(s).append(value);
                    s = sep;
                    sepEmptyFlag = false;
                }
            }
        } else {
            for (T value : values) {
                buffer.append(s).append(value);
                s = sep;
            }
        }
        buffer.append(suffix);
        return buffer.toString();
    }
    
    public static String toString(Object value, int repeateCount, String prefix, String suffix,
            String sep, boolean ignoreNullOrEmpty) {
        if (ignoreNullOrEmpty) {
            if (value instanceof String) {
                if (StringUtils.isEmpty((String) value)) {
                    return prefix + suffix;
                }
            } else if (value == null) {
                return prefix + suffix;
            }
        }
        StringBuffer buffer = new StringBuffer();
        String s = prefix;
        buffer.append(s);
        s = StringUtils.EMPTY;
        int i = repeateCount;
        while (i > 0) {
            buffer.append(s).append(value);
            s = sep;
            i--;
        }
        buffer.append(suffix);
        return buffer.toString();
    }
    
    public static void main(String[] args) {
        System.out.println(toString(1, 5, "(", ")", ",", false));
        
        Set<String> set = new HashSet<String>();
        for(int i=0; i<100;i++){
            set.add(""+i);
        }
        List<List<String>> list = (List<List<String>>) splitSetToList(set, 3);
        for(List<String> object : list){
            System.out.println(object.toString());
        }
    }
    
    public static String[] toStringArray(List<String> list){
        
        if(CollectionUtils.notEmpty(list)){
            String[] array = new String[list.size()];
            for(int i=0;i<list.size();i++){
                array[i] = list.get(i);
            }
            return array;
        }
        return null;
    }

    /**
     * 
     * @param array
     * @param includeNull 为null的对象是否放入
     * @return
     */
    public static List<String> toArrayList(String[] array, boolean includeNull) {

        if (CollectionUtils.notEmpty(array)) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < array.length; i++) {
                if (!includeNull && (array[i] == null || array[i].isEmpty()) ) {
                    continue;
                }
                list.add(array[i]);
            }
            return list;
        }
        return null;
    }
}
