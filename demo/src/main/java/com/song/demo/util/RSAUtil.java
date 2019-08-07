package com.song.demo.util;


import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @ClassName: RSAUtil
 * @Description: //TODO(RSA加密)
 * @Author: 宋正健
 * @Date: 2019/8/7 15:59
 * @Version: 1.0
 **/
public class RSAUtil {

    //私钥对数据进行加密
    public static byte[] encryptByPrivateKey(byte[] data, String key) {
        byte[] bytes=null;
        try {
            // 对密钥解密
            byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
            // 取得私钥
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            bytes = cipher.doFinal(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }


}
