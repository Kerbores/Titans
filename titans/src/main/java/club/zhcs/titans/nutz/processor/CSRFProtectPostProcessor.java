package club.zhcs.titans.nutz.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;


/**
 * 配合CSRFProtectPreProcessor使用
 * 安全性拦截器，拦截常见攻击如：CSRF漏洞
 * 配合前端AJAX SETUP使用，在发送AJAX请求时寻找Hidden表单token值存入header
 * @author JiangKun
 * @date 2016年9月12日
 */
public class CSRFProtectPostProcessor extends AbstractProcessor{

	Log log = Logs.get();
	
	@Override
	public void process(ActionContext ac) throws Throwable {
		HttpServletRequest request = ac.getRequest();
		//针对POST拦截，规范提交数据到后台必须用POST方法
		if("POST".equalsIgnoreCase(request.getMethod())){
			if(CSRFTokenManager.hasValidRequestTokenHeader(request)){
				doNext(ac);
			}else{
				log.warn("Invalid request header,could not find a valid CSRF_TOKEN!");
				//不调用doNext 直接响应为非法请求
				ac.getResponse().sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid request header,could not find a valid CSRF_TOKEN!");
			}
		}else{
			doNext(ac);
		}
	}

}
