package com.nostyling.proxy.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shiliang
 * @Classname Test
 * @Date 2021/1/21 20:06
 * @Description TODO
 */
public class ListUtils {
    private static final Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");

    public static void main(String[] args) {
        List<Test1> test1s = Arrays.asList(new Test1[]{
                        new Test1("红色", "90.1", "AADFA", "对象1"),

                        new Test1("绿色", "100", "jyhyteurea", "对象2"),

                        new Test1("黄色", "52", "adfasfd", "对象3"),
                        new Test1("红色", "12.1", "dfgae", "对象4"),
                        new Test1("绿色", "22", "ghfdgse", "对象5"),

                        new Test1("红色", "32", "AAeFafdafA", "对象6"),
                        new Test1("黄色", "23", "geaewa", "对象7"),
                        new Test1("黄色", "22", "agrqaxfr", "对象8"),

                        new Test1("绿色", "111", "rthjfdgads", "对象9"),
                        new Test1("红色", "12.1", "ffsdfs3fff", "对象10"),
                        new Test1("黄色", "77", "adfasdfa", "对象11"),

                        new Test1("绿色", "200", "asdfdffds", "对象12")
                }

        );
        System.out.println("排序前list" + test1s.toString());
        String[] strings = {"红色,黄色,绿色"};
        String[] strings2 = {"绿色,黄色,红色"};
        // 可以试试 将 strings 换为 strings2，再看看效果
        ListUtils.sort(test1s, new String[]{"colour", "nullValue", "fieldName"}, new boolean[]{true, true, true}, strings);
        System.out.println();
        System.out.println("排序后list" + test1s.toString());
        System.out.println();
    }


    /**
     * 对list的元素按照多个属性名称排序,
     * list元素的属性可以是数字（byte、short、int、long、float、double等，支持正数、负数、0）、char、String、java.util.Date
     *
     * @param list
     * @param sortNameArr list元素的属性名称
     * @param isAsc       true升序，false降序
     */
    // public static <E> void sort(List<E> list, final boolean isAsc, final String... sortNameArr) {
    //     Collections.sort(list, new Comparator<E>() {
    //
    //         @Override
    //         public int compare(E a, E b) {
    //             int ret = 0;
    //             try {
    //                 for (int i = 0; i < sortNameArr.length; i++) {
    //                     ret = ListUtils.compareObject(sortNameArr[i], isAsc, a, b);
    //                     if (0 != ret) {
    //                         break;
    //                     }
    //                 }
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //             return ret;
    //         }
    //     });
    // }

    /**
     * 给list的每个属性都指定是升序还是降序
     *
     * @param list
     * @param sortnameArr 参数数组
     * @param typeArr     每个属性对应的升降序数组， true升序，false降序
     * @param sort        自定义排序的顺序
     * @param <E>
     */
    public static <E> void sort(List<E> list, final String[] sortnameArr, final boolean[] typeArr, final String[] sort) {
        if (sortnameArr.length != typeArr.length) {
            throw new RuntimeException("属性数组元素个数和升降序数组元素个数不相等");
        }
        Collections.sort(list, new Comparator<E>() {
            @Override
            public int compare(E a, E b) {
                int ret = 0;
                try {
                    for (int i = 0; i < sortnameArr.length; i++) {
                        ret = ListUtils.compareObject(sortnameArr[i], typeArr[i], a, b, i < sort.length ? sort[i] : null);
                        if (0 != ret) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ret;
            }
        });
    }

    /**
     * 对2个对象按照指定属性名称进行排序
     *
     * @param sortname 属性名称
     * @param isAsc    true升序，false降序
     * @param sort    自定义排序的顺序
     * @param a
     * @param b
     * @return
     * @throws Exception
     */
    private static <E> int compareObject(final String sortname, final boolean isAsc, E a, E b, final String sort) throws Exception {
        int ret;
        Object value1 = ListUtils.forceGetFieldValue(a, sortname);
        Object value2 = ListUtils.forceGetFieldValue(b, sortname);
        String str1 = value1.toString();
        String str2 = value2.toString();

        if (isNumeric(str1) && isNumeric(str2)) {
            BigDecimal bigDecimal = new BigDecimal(str1);
            BigDecimal bigDecimal2 = new BigDecimal(str2);
            if (isAsc) {
                ret = bigDecimal.compareTo(bigDecimal2);
            } else {
                ret = bigDecimal2.compareTo(bigDecimal);
            }
            return ret;
        } else if (value1 instanceof Date && value2 instanceof Date) {
            long time1 = ((Date) value1).getTime();
            long time2 = ((Date) value2).getTime();
            int maxlen = Long.toString(Math.max(time1, time2)).length();
            str1 = ListUtils.addZero2Str(time1, maxlen);
            str2 = ListUtils.addZero2Str(time2, maxlen);
        }
        if (StringUtils.isNotBlank(sort)) {
            String[] splits = sort.split(",");
            Integer s1 = null;
            Integer s2 = null;
            for (int i = 0; i < splits.length; i++) {
                if (null == s1 && StringUtils.equals(splits[i], str1)) {
                    s1 = i;
                }
                if (null == s2 && StringUtils.equals(splits[i], str2)) {
                    s2 = i;
                }
            }
            if (null == s1 && null == s2) {
                if (isAsc) {
                    ret = str1.compareTo(str2);
                } else {
                    ret = str2.compareTo(str1);
                }
                return ret;
            } else {
                if (null == s1) {
                    s1 = s2 + 1;
                }
                if (null == s2) {
                    s2 = s1 + 1;
                }
                if (isAsc) {
                    ret = s1.compareTo(s2);
                } else {
                    ret = s2.compareTo(s1);
                }

                return ret;
            }


        }
        if (isAsc) {
            ret = str1.compareTo(str2);
        } else {
            ret = str2.compareTo(str1);
        }

        return ret;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 给数字对象按照指定长度在左侧补0.
     * <p>
     * 使用案例: addZero2Str(11,4) 返回 "0011", addZero2Str(-18,6)返回 "-000018"
     *
     * @param numObj 数字对象
     * @param length 指定的长度
     * @return
     */
    public static String addZero2Str(Number numObj, int length) {
        NumberFormat nf = NumberFormat.getInstance();
        // 设置是否使用分组
        nf.setGroupingUsed(false);
        // 设置最大整数位数
        nf.setMaximumIntegerDigits(length);
        // 设置最小整数位数
        nf.setMinimumIntegerDigits(length);
        return nf.format(numObj);
    }

    /**
     * 获取指定对象的指定属性值（去除private,protected的限制）
     *
     * @param obj       属性名称所在的对象
     * @param fieldName 属性名称
     * @return
     * @throws Exception
     */
    public static Object forceGetFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        Object object = null;
        boolean accessible = field.isAccessible();
        if (!accessible) {
            // 如果是private,protected修饰的属性，需要修改为可以访问的
            field.setAccessible(true);
            object = field.get(obj);
            // 还原private,protected属性的访问性质
            field.setAccessible(accessible);
            return object;
        }
        object = field.get(obj);
        return object;
    }
}
