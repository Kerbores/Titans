package club.zhcs.titans.utils.net;

import java.util.Map;

import org.nutz.http.Header;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.sender.PostSender;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

/**
 * URL接口调用器
 * 
 * @author Ixion
 *
 *         create at 2014年8月22日
 */
public class UrlPoster {

	/**
	 * 发送一个post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param params
	 *            参数map
	 * @return 响应串
	 */
	public static String simpleUTF8Post(String url, Map params) {
		return new PostSender(Request.create(url, METHOD.POST, params, header)).send().getContent();
	}

	/**
	 * 发送一个post请求
	 * 
	 * @param url
	 *            请求路径
	 * @param query
	 *            请求参数
	 * @return 响应串
	 */
	public static String simpleUTF8Post(String url, String query) {
		return simpleUTF8Post(url, toMap(query));
	}

	/**
	 * @param query
	 * @return
	 */
	private static Map toMap(String query) {
		Map params = new NutMap();
		String[] infos = query.split("&");
		for (String info : infos) {
			params.put(info.split("=")[0], info.split("=")[1]);
		}
		return params;
	}

	/**
	 * 上传文件
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数map
	 * @param fileParams
	 *            文件参数map 支持:
	 * 
	 *            <pre>
	 * 					key-file,key-File[],key-Collection<File>
	 * </pre>
	 * @return 响应串
	 */
	public static String upload(String url, Map params, Map fileParams) {
		params.putAll(fileParams);
		return new IxionFileUploadPoster(Request.create(url, METHOD.POST, params, header)).send().getContent();
	}

	private static Header header;

	static {
		Map properties = new NutMap();
		properties.put("connection", "Keep-Alive");
		properties.put("Charset", "UTF-8");
		properties.put("from", "UrlPoster");
		if (Lang.isWin()) {// WINDOWS 用户都高大上使用 WIN8.1 IE11
			properties.put("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		} else if (Lang.isAndroid) {// ANDROID用户也很高大上都是ANDROID4.4 UC浏览器
			properties
					.put("user-agent",
							"Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; MI 3C Build/KTU84P) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1");
		} else {// 其他默认使用乌班图下的Chrome
			properties.put("user-agent",
					"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.11 (KHTML, like Gecko) Ubuntu/11.10 Chromium/27.0.1453.93 Chrome/27.0.1453.93 Safari/537.36");
		}
		header = Header.create(properties);
	}

}
