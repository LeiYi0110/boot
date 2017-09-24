package com.bjxc.utils.encide;

import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密解密工具�?
 */
public class CyptoUtils {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	
    /**
     * DES算法，加�?
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8�?
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws InvalidAlgorithmParameterException 
     * @throws Exception 
     */
    public static String encode(String key,String data) {
    	if(data == null)
    		return null;
    	try{
    		// DESKeySpec,指定�?�? DES 密钥,
            // new DESKeySpec(key.getBytes()) : 创建�?�? DESKeySpec 对象，使�? key 中的�? 8个字节作�? DES 密钥的密钥内容�??
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            // 生成指定秘密密钥算法�? SecretKeyFactory 对象�?
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // 根据提供的密钥规范（密钥材料）生�? SecretKey 对象。key的长度不能够小于8位字�?
            Key secretKey = keyFactory.generateSecret(dks);
            // 此类为加密和解密提供密码功能。返回实现指定转换的 Cipher 对象�?
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            //此类指定�?个初始化向量 (IV):使用 iv 中的字节作为 IV 来构造一�? IvParameterSpec 对象�?
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            //加密参数的（透明）规范�?�此接口不包含任何方法或常量。它仅用于将�?有参数规范分组，并为其提供类型安全�?�所有参数规范都必须实现此接口�??
            AlgorithmParameterSpec paramSpec = iv;
            //用密钥和�?组算法参数初始化�? Cipher。ENCRYPT_MODE: 用于�? Cipher 初始化为加密模式的常量�??
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            // 按单部分操作加密或解密数据，或�?�结束一个多部分操作�?
            byte[] bytes = cipher.doFinal(data.getBytes());
            // 将bytes数组，转�?16进制的大写字符串
            return byte2hex(bytes);
    	}catch(Exception e){
    		e.printStackTrace();
    		return data;
    	}
    }

    /**
     * DES算法，解�?
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8�?
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key,String data) {
    	if(data == null)
    		return null;
        try {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key的长度不能够小于8位字�?
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return new String(cipher.doFinal(hex2byte(data.getBytes())));
        } catch (Exception e){
    		e.printStackTrace();
    		return data;
        }
    }

	/**
	 * 二行制转字符�?
	 * @param b
	 * @return
	 */
    private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b!=null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
    
    private static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
		    String item = new String(b,n,2);
		    b2[n/2] = (byte)Integer.parseInt(item,16);
		}
        return b2;
    }
    
}
