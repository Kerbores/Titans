package club.zhcs.titans.nutz.interceptor;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project Utils
 *
 * @file UaInterceptor.java
 *
 * @description useragent拦截
 *
 * @time 2016年3月8日 上午10:51:26
 *
 */
@IocBean(name = "uaDebug")
public class UserAgentInterceptor implements MethodInterceptor {
	Log log = Logs.getLog(UserAgentInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nutz.aop.MethodInterceptor#filter(org.nutz.aop.InterceptorChain)
	 */
	@Override
	public void filter(InterceptorChain chain) throws Throwable {
		UserAgent ua = new UserAgent(Mvcs.getReq().getHeader("user-agent"));
		String uaInfo = "ip地址: " + Mvcs.getReq().getRemoteAddr() + " userAgent信息:" + Json.toJson(ua);
		log.debug(uaInfo);
		chain.doChain();
	}

}
