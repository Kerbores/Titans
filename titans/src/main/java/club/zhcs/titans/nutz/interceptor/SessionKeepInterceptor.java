package club.zhcs.titans.nutz.interceptor;

import javax.servlet.http.Cookie;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.Mvcs;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project titans
 *
 * @file SessionKeepInterceptor.java
 *
 * @description 会话保持
 *
 * @copyright 内部代码,禁止转发
 *
 * @time 2016年5月16日 上午10:49:48
 *
 */
@IocBean(name = "sessionKeeper")
public class SessionKeepInterceptor implements MethodInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nutz.aop.MethodInterceptor#filter(org.nutz.aop.InterceptorChain)
	 */
	@Override
	public void filter(InterceptorChain arg0) throws Throwable {
		_addCookie("JSESSIONID", Mvcs.getReq().getSession().getId(), 3600);
	}

	protected void _addCookie(String name, String value, int age) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(age);
		Mvcs.getResp().addCookie(cookie);
	}

}
