package club.zhcs.titans.nutz.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * 配合CSRFProtectPreProcessor使用 安全性拦截器，拦截常见攻击如：CSRF漏洞 配合前端AJAX
 * SETUP使用，在发送AJAX请求时寻找Hidden表单token值存入header
 * 
 * @author JiangKun
 * @date 2016年9月12日
 */
public class CSRFProtectPostProcessor extends AbstractProcessor {

	Log log = Logs.get();

	@Override
	public void process(ActionContext ac) throws Throwable {
		HttpServletRequest request = ac.getRequest();
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {// 针对ajax请求进行拦截吧
			if (CSRFTokenManager.hasValidRequestTokenHeader(request)) {
				doNext(ac);
			} else {
				log.warn("Invalid request header,could not find a valid CSRF_TOKEN!");
				ac.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid request header,could not find a valid CSRF_TOKEN!");
			}
		} else {
			doNext(ac);
		}
	}

}
