package com.bootdo.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 描述:常用工具类
 *
 * @author JunoGuan
 * @create 2018-08-08 16:03
 */
public class CommonUtils {

    public static final String EMPTY = StringUtils.EMPTY;

    private static final Integer[] BASE_NUM_ARRAY = new Integer[] {0,1,2,3,4,5,6,7,8,9};

    private static final String[] BASE_STR_ARRAY = new String[] { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z" ,"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    //-----常用方法
    public static final boolean isBlank(CharSequence cs){
        return StringUtils.isBlank(cs);
    }

    public static final boolean isEmpty(CharSequence cs){
        return StringUtils.isEmpty(cs);
    }

    public static final boolean isEmpty(Collection collection){
        return CollectionUtils.isEmpty(collection);
    }

    public static final boolean isEmpty(Map map){
        return MapUtils.isEmpty(map);
    }

    public static final String trim(String str){
        return StringUtils.trim(str);
    }

    public static final String trimToEmpty(String str){
        return StringUtils.trimToEmpty(str);
    }

    public static final String trimToNull(String str){
        return StringUtils.trimToNull(str);
    }

    public static final boolean isNotBlank(CharSequence cs){
        return StringUtils.isNotBlank(cs);
    }

    public static final boolean isNotEmpty(CharSequence cs){
        return StringUtils.isNotEmpty(cs);
    }

    public static final boolean isNotEmpty(Collection collection){
        return CollectionUtils.isNotEmpty(collection);
    }

    public static final boolean isNotEmpty(Map map){
        return MapUtils.isNotEmpty(map);
    }

    public static final int length(CharSequence cs){
        return StringUtils.length(cs);
    }

    public static final int size(Collection collection){
        return CollectionUtils.size(collection);
    }

    public static final int size(Map map){
        return CollectionUtils.size(map);
    }

    public static final String getString(CharSequence cs,CharSequence defaultCs){
        return (isBlank(cs)?(isEmpty(defaultCs)?EMPTY:defaultCs):cs).toString();
    }

    public static final String getStringWithTrim(CharSequence cs,CharSequence defaultCs){
        return (isBlank(trimToNull(cs.toString()))?(isEmpty(defaultCs)?EMPTY:defaultCs):cs).toString();
    }

    public static final String getString(CharSequence cs){
        return getString(cs,null);
    }

    public static final String getStringWithTrim(CharSequence cs){
        return getStringWithTrim(cs,null);
    }


    public static final boolean isTrue(Object o){
        if(o==null) return false;
        if(o instanceof Boolean)    return ((Boolean)o).booleanValue();
        if(o instanceof Number)     return ((Number)o).intValue()>0;
        if(o instanceof String){
            String str  = (String) o;
            return "TRUE".equalsIgnoreCase(str)||"T".equalsIgnoreCase(str)||"1".equalsIgnoreCase(str);
        }
        return isTrue(o.toString());
    }

    public static final <T extends Number> boolean equals(T t1, T t2){
       return equals(t1,t2,true);
    }

    public static final <T extends Number> boolean equals(T t1, T t2,boolean nullResult){
        if(t1==null&&t2==null)  return nullResult;
        if(t1==null||t2==null)  return false;
        if(t1 instanceof Double||t2 instanceof Double){
            return t1.doubleValue()==t2.doubleValue();
        }
        return t1.intValue()==t2.intValue();
    }

    public static final boolean equals(String str1,String str2){
        return StringUtils.equals(str1,str2);
    }
    public static final boolean equalsIgnoreCase(String str1,String str2){
        return StringUtils.equalsIgnoreCase(str1,str2);
    }

    public static final <K,V> Map<K,V> getMapping(Collection<V> data,String prop,Class<V> valClazz){
        Assert.hasText(prop,"prop must be not null.");
        Map<K,V> mapping = new HashMap<>(size(data));
        if(isEmpty(data)) return mapping;
        PropertyDescriptor descriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(valClazz,prop);
        try {
            for (V datum : data) {
                K key = (K) descriptor.getReadMethod().invoke(datum);
                mapping.put(key,datum);
            }
        } catch (Exception e) {
            throw new RuntimeException("get key["+prop+"] from "+valClazz.getName()+"error");
        }
        return mapping;
    }

    public static final <T,X> List<T> getList(Collection<X> data,String prop){
        Assert.hasText(prop,"prop must be not null.");
        List<T> result = null;
        if(isEmpty(data))   return Collections.emptyList();
        Class<X> xClass = null;
        PropertyDescriptor descriptor = null;
        result = new ArrayList<>(data.size());
        for (X datum : data) {
            if(datum==null) continue;
            if(xClass==null){
                xClass = (Class<X>) datum.getClass();
                descriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(xClass,prop);
                if(descriptor==null)    throw new RuntimeException("prop:"+prop+"not found in Class:"+xClass.getName());
            }
            try {
                T val = (T) descriptor.getReadMethod().invoke(datum);
                result.add(val);
            } catch (Exception e) {
                throw new RuntimeException("get key["+prop+"] from "+xClass.getName()+"error");
            }
        }
        return result;
    }


    //---简易id生成

    /**
     * 获取UUID替换里面的-字符
     *
     * @return
     */
    public final static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 字符串加上UUID
     *
     * @param pre 字符串类型参数
     * @return
     */
    public final static String getUUID(String pre) {
        return pre + getUUID();
    }

    /**
     * 返回20 number char
     *
     * @return 20 number char
     */
    public synchronized final static String getKey() {
        long l = System.currentTimeMillis();
        double d = Math.random();
        StringBuffer sb = new StringBuffer();
        sb.append(d);
        sb.delete(0, sb.length() - 7);
        sb.insert(0, l);
        return sb.toString();
    }

    //---随机方法

    /**
     * 随机获取n个字符的字符串
     * @param num
     * @return
     */
    public static final String getRandomChar(int num){
        if(num==0)  return EMPTY;
        int length = BASE_STR_ARRAY.length;
        List<String> list = Arrays.asList(BASE_STR_ARRAY);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        Random random=new Random();
        for (int i = 0; i < num; i++) {
            sb.append(list.get(random.nextInt(length)));
        }
        return sb.toString();
    }

    /**
     * 随机获取n个数字的字符串
     * @param num
     * @return
     */
    public static final String getRandomNumStr(int num){
        if(num==0)  return EMPTY;
        int length = BASE_NUM_ARRAY.length;
        List<Integer> list = Arrays.asList(BASE_NUM_ARRAY);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        Random random=new Random();
        for (int i = 0; i < num; i++) {
            sb.append(list.get(random.nextInt(length)));
        }
        return sb.toString();
    }

    public static final String omitString(String str,int length){
        if(isBlank(str))   return EMPTY;
        str = str.trim();
        if(str.length()<=length)
            return str;
        str = str.substring(0,length);
        return str+"…";
    }

    public static void main(String[] args) {
        System.out.println(omitString("你好啊",3));
    }


}
