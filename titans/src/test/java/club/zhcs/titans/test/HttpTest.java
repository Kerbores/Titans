package club.zhcs.titans.test;

import org.junit.Test;
import org.nutz.http.Http;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project titans
 *
 * @file HttpTest.java
 *
 * @description http请求测试
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月18日 上午11:34:25
 *
 */
public class HttpTest {

	@Test
	public void test() {
		System.setProperty("jsse.enableSNIExtension", "false");
		System.err.println(Http.get("https://login.weixin.qq.com/jslogin").getContent());
	}
}
