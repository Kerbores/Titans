package club.zhcs.titans.test;

import org.junit.Test;

import club.zhcs.titans.utils.codec.AES;
import club.zhcs.titans.utils.codec.DES;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project titans
 *
 * @file CodecTest.java
 *
 * @description 加密解密
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月16日 下午10:29:38
 *
 */
public class CodecTest {

	@Test
	public void aes() {
		String abc = "java使用AES加密解密 AES-128-ECB加密";

		String result = AES.encrypt(abc);
		System.err.println(result);

		System.err.println(AES.decrypt(result));
	}

	@Test
	public void des() {

		String abc = "java使用AES加密解密 AES-128-ECB加密";

		String result = DES.encrypt(abc);
		System.err.println(result);

		System.err.println(DES.decrypt(result));
	}
}
