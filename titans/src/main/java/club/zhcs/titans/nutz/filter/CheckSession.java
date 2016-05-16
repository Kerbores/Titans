package club.zhcs.titans.nutz.filter;

import java.util.Arrays;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.ActionFilter;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.View;
import org.nutz.mvc.view.ServerRedirectView;

/**
 * 检查当前 Session，如果存在某一属性，并且不为 null，则通过 <br>
 * 否则，返回一个 ServerRecirectView 到对应 path
 * <p>
 * 构造函数需要两个参数
 * <ul>
 * <li>第一个是， 需要检查的属性名称。如果 session 里存在这个属性，则表示通过检查
 * <li>第二个是，如果未通过检查，将当前请求转向何处。 一个类似 /yourpath/xxx.xx 的路径
 * </ul>
 * 
 * @author zozoh(zozohtnt@gmail.com)
 */
public class CheckSession implements ActionFilter {

	private String name;
	private String path;
	private String[] paths;
	private String allowedPaths;

	public CheckSession(String name, String path, String allowedPaths) {
		this.path = path;
		paths = allowedPaths.split(",");
		Lang.each(paths, new Each<String>() {

			@Override
			public void invoke(int index, String ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				ele.trim();
			}
		});
		Arrays.sort(paths);
		this.name = name;
	}

	public String getAllowedPaths() {
		return allowedPaths;
	}

	public void setAllowedPaths(String allowedPaths) {
		this.allowedPaths = allowedPaths;
	}

	public View match(ActionContext context) {
		String contentPath = Mvcs.getServletContext().getContextPath();
		String URI = Mvcs.getReq().getRequestURI();
		URI = URI.substring(contentPath.length());
		if (Mvcs.getReq().getSession().getAttribute(name) == null && Arrays.binarySearch(paths, URI) < 0)
			return new ServerRedirectView(path);
		return null;
	}

}
