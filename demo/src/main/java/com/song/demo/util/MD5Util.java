package com.song.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: MD5Util
 * @Description: //TODO(md5加密)
 * @Author: 宋正健
 * @Date: 2019/8/7 16:30
 * @Version: 1.0
 **/

public class MD5Util {

    public static String md5(String data){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] array = md.digest(data.getBytes());
        StringBuffer sb = new StringBuffer();
        for(byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }

}
