package club.zhcs.titans.nutz.processor;

import javax.servlet.http.HttpServletRequest;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.impl.processor.AbstractProcessor;

/**
 * @author JiangKun
 * @date 2016年9月12日
 */
public class XSSProtectProcessor extends AbstractProcessor{

	@Override
	public void process(ActionContext ac) throws Throwable {
		HttpServletRequest wrapperRequest = new XssHttpServletRequestWrapper(ac.getRequest());
		ac.setRequest(wrapperRequest);
		//TODO 兽总说这2个方式是一样的
		//ac.getRequest() == Mvcs.getReq()
		doNext(ac);
	}

}
