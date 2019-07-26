package com.song.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: SnGenerator
 * @Description: //TODO(生成订单号)
 * @Author: 宋正健
 * @Date: 2019/7/26 15:56
 * @Version: 1.0
 **/

public class SnGenerator {

    private final static char[] NUMS = "123456789".toCharArray();
    private final static char[] LETTERS = "QWERTYUIPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm".toCharArray();
    private final static char[] MIX_LETTERS_AND_NUM = "QWERTYUIPASDFGHJKLZXCVBNMqwertyuipasdfghjklzxcvbnm01234567890".toCharArray();

    public final static int MODE_NUM = 0;
    public final static int MODE_LOWER_STR = 1;
    public final static int MODE_UPPER_STR = 2;
    public final static int MODE_STR = 3;
    public final static int MODE_MIX = 4;


    /**
     * 生成带前缀的字符串，如果前缀+日期字符串+随机字符串的长度超过count，将会在保留前缀的情况下压缩其余部分
     *
     * @param prefix
     * @param count
     * @param mode
     * @return
     */
    public static String generateFormatWithPrefix(String prefix, int count, int mode) {
        return generateFormat(prefix, null, count, mode);
    }

    /**
     * 生成带前缀的字符串，如果日期字符串+随机字符串+后缀的长度超过count，将会在保留后缀的情况下压缩其余部分
     *
     * @param suffix
     * @param count
     * @param mode
     * @return
     */
    public static String generateFormatWithSuffix(String suffix, int count, int mode) {
        return generateFormat(null, suffix, count, mode);
    }

    /**
     * 生成不带前后缀的字符串，格式为yyyyMMddHHmmssSSS+随机字符串，长度为count
     *
     * @param count
     * @param mode
     * @return
     */
    public static String generateFormat(int count, int mode) {
        return generateFormat(null, null, count, mode);
    }

    /**
     * 生成格式化的字符串，格式为前缀+日期字符串(17位)+中间随机字符串+后缀。如果前缀+后缀+中间字符串的长度超过count，
     * 将会压缩中间字符串的长度来满足count
     *
     * @param prefix 字符串前缀
     * @param suffix 字符串后缀
     * @param count  生成字符串的长度
     * @param mode   模式
     * @return
     */
    public static String generateFormat(String prefix, String suffix, int count, int mode) {
        if (count <= 17) {
            count = 18;
        }
        int prefixLen = 0;
        int suffixLen = 0;
        StringBuilder sb = new StringBuilder();
        if (prefix != null && (!"".equals(prefix))) {
            prefixLen = prefix.length();
            sb.append(prefix);
        }
        if (suffix != null && (!"".equals(suffix))) {
            suffixLen = suffix.length();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());

        int len = count - prefixLen - suffixLen - date.length();
        if (len > 0) {
            switch (mode) {
                case MODE_NUM:
                    date = date + randomNums(len);
                    break;
                case MODE_LOWER_STR:
                    date = date + randomLowerStr(len);
                    break;
                case MODE_UPPER_STR:
                    date = date + randomUpperStr(len);
                    break;
                case MODE_STR:
                    date = date + randomStr(len);
                    break;
                case MODE_MIX:
                    date = date + randomMix(len);
                    break;
                default:
                    date = date + randomNums(len);
                    break;
            }
        }
        sb.append(date.substring(0, count - prefixLen - suffixLen));
        if (suffixLen > 0) {
            sb.append(suffix);
        }

        return sb.toString();
    }

    /**
     * 生成写字母和数字随机字符串
     *
     * @param count
     * @return
     */
    public static String randomMix(int count) {
        return generator(count, MIX_LETTERS_AND_NUM);
    }

    /**
     * 生成大小写混合字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomStr(int count) {
        return generator(count, LETTERS);
    }

    /**
     * 生成纯大写字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomUpperStr(int count) {
        return generator(count, LETTERS).toUpperCase();
    }

    /**
     * 生成纯数字随机字符串
     *
     * @param count
     * @return
     */
    public static String randomNums(int count) {
        return generator(count, NUMS);
    }

    /**
     * 生成纯小写字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomLowerStr(int count) {
        return generator(count, LETTERS).toLowerCase();
    }

    /**
     * 生成器
     *
     * @param count
     * @param arr
     * @return
     */
    private static String generator(int count, char[] arr) {
        if (count <= 0) {
            count = 6;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            double d = Math.random();
            int index = (int) Math.floor(d * arr.length);
            sb.append(arr[index]);
        }

        return sb.toString();
    }



}
