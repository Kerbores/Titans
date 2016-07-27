package club.zhcs.titans.nutz.processor;

import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionInfo;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.impl.processor.FailProcessor;

import club.zhcs.titans.utils.db.Result;

/**
 * @author 王贵源<kerbores>
 *
 *         create at 2015年12月9日 下午3:20:17
 */
public class KerboresFailProcessor extends FailProcessor {
	Log log = Logs.get();
	private String okView;

	@Override
	public void init(NutConfig config, ActionInfo ai) throws Throwable {
		okView = ai.getOkView();
		view = evalView(config, ai, ai.getFailView());
	}

	@Override
	public void process(ActionContext ac) throws Throwable {
		if (Strings.equals(okView, "json") && ac.getError() != null) {// 产生了错误而且是json视图
			log.debugf("response json error msg with %s ", this.getClass().getName());
			ac.getError().printStackTrace();
			Mvcs.write(ac.getResponse(), Result.exception(ac.getError().getMessage()), JsonFormat.nice());// 写回错误信息
			return;
		}
		super.process(ac);
	}
}
