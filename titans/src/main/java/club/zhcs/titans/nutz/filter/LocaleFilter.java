package club.zhcs.titans.nutz.filter;

import java.util.Locale;

import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;

public abstract class LocaleFilter implements ActionFilter {

	@Override
	public View match(ActionContext context) {
		Mvcs.setLocalizationKey(Locale2String(context.getRequest().getLocale()));
		return null;
	}

	protected abstract String Locale2String(Locale locale);

}
