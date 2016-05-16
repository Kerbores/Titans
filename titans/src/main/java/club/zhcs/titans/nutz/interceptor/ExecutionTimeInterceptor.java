package club.zhcs.titans.nutz.interceptor;

import org.nutz.aop.InterceptorChain;
import org.nutz.aop.MethodInterceptor;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 执行时间aop拦截器
 * 
 * @author Kerbores
 *
 *         Create At 2015年1月5日 下午3:51:40
 */
@IocBean(name = "aopTimer")
public class ExecutionTimeInterceptor implements MethodInterceptor {

	private static Log LOG = Logs.getLog(ExecutionTimeInterceptor.class);

	@Override
	public void filter(InterceptorChain chain) throws Throwable {
		if (!LOG.isDebugEnabled()) {
			chain.doChain();
			return;
		}
		Stopwatch stopwatch = Stopwatch.begin();
		chain.doChain();
		stopwatch.stop();
		LOG.debugf("ExecutionTime %d ms in %s", stopwatch.getDuration(), chain.getCallingMethod());

	}

}
