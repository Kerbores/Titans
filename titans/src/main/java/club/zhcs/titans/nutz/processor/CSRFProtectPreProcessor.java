package club.zhcs.titans.nutz.processor;

import javax.servlet.http.HttpServletRequest;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * 配合CSRFProtectPostProcessor使用
 * eg:
 * 1、模板layout主页面body中加上以下标签
   	<input type="hidden" id="CSRFToken" value="${CSRFToken}" />
   2、
	//jQuery AJAX全局setup
	var token = $("#CSRFToken").val();
	if(token){
		var _headers = {"RequestVerificationToken":token};
		option["headers"] = _headers;
		$.ajaxSetup(option);  
	};
 * @author JiangKun
 * @date 2016年9月12日
 */
public class CSRFProtectPreProcessor extends AbstractProcessor{

	Log log = Logs.get();
	
	@Override
	public void process(ActionContext ac) throws Throwable {
		HttpServletRequest request = ac.getRequest();
		//针对页面请求拦截处理,非AJAX的GET请求
		if(!"XMLHttpRequest".equals(request.getHeader("X-Requested-With")) && "GET".equalsIgnoreCase(request.getMethod())){
			//提供给layout展示
			request.setAttribute("CSRFToken", CSRFTokenManager.getTokenForSession(request.getSession()));
		}
		doNext(ac);
	}
	

}
