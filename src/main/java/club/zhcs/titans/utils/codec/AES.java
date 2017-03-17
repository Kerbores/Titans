package club.zhcs.titans.utils.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project titans
 *
 * @file AES.java
 *
 * @description AES加密解密
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月16日 下午10:24:20
 *
 */
public class AES {

	private final static String AES = "AES";

	public static String DEFAULT_KEY = "abcdefghhgfedcba";

	private static Log log = Logs.get();

	/**
	 * 使用默认密钥加密
	 * 
	 * @param sSrc
	 *            明文
	 * @return 密文
	 */
	public static String encrypt(String sSrc) {
		return encrypt(sSrc, DEFAULT_KEY);
	}

	/**
	 * 加密
	 * 
	 * @param sSrc
	 *            明文
	 * @param sKey
	 *            密钥
	 * @return 密文
	 */
	public static String encrypt(String sSrc, String sKey) {
		try {
			if (sKey == null) {
				log.error("key 不能为空!");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				log.error("key 长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return Base64.encodeToString(encrypted, false);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	/**
	 * 解密(使用默认密钥)
	 * 
	 * @param sSrc
	 *            密文
	 * @return 明文
	 */
	public static String decrypt(String sSrc) {
		return decrypt(sSrc, DEFAULT_KEY);
	}

	/**
	 * 解密
	 * 
	 * @param sSrc
	 *            密文
	 * @param sKey
	 *            密钥
	 * @return 明文
	 */
	public static String decrypt(String sSrc, String sKey) {
		try {
			if (sKey == null) {
				log.error("key 不能为空!");
				return null;
			}
			if (sKey.length() != 16) {
				log.error("key 长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = Base64.decode(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception e) {
			log.equals(e);
			return null;
		}
	}

}
