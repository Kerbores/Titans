package club.zhcs.titans.utils.net;

import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.lang.Lang;

import club.zhcs.titans.utils.db.Result;

/**
 * 用于url json数据接口的json解析
 * 
 * @author 贵源<br>
 *         Create at 2014年3月5日
 */
public class InvokeParser {
	/**
	 * 解析json数组
	 * 
	 * @param info
	 *            json数据信息
	 * @return 数据表示的List
	 */
	public static List parseAsList(String info) {
		if (info.equals("SERVER_EXCEPTION")) {
			throw new InvokeException();
		}
		return (List) Json.fromJson(info);
	}

	/**
	 * 解析成map 使用单个对象
	 * 
	 * @param info
	 *            json数据
	 * @return Map实例
	 */
	public static Map parseMap(String info) {
		if (info.equals("SERVER_EXCEPTION")) {
			throw new InvokeException();
		}
		return Lang.map(info);
	}

	/**
	 * 解析成result
	 * 
	 * @param info
	 *            json信息
	 * @return Result实例
	 */
	public static Result parseResult(String info) {
		if (info.equals("SERVER_EXCEPTION")) {
			return Result.exception();
		}
		return Lang.map2Object(Lang.map(info), Result.class);
	}

	/**
	 * 解析简单数据 比如string数组或者数字数组
	 * 
	 * @param info
	 *            json信息
	 * @return list
	 */
	public static List parseSimpleList(String info) {
		if (info.equals("SERVER_EXCEPTION")) {
			throw new InvokeException();
		}
		return (List) Json.fromJson(info);
	}

	/**
	 * 复杂对象解析 list<map>形式map里面如果还有对象也是map 所有的map都是java.util.LinkedHashMap实现
	 * 
	 * @param info
	 *            json数据
	 * @return List实例
	 */
	public List parse(String info) {
		if (info.equals("SERVER_EXCEPTION")) {
			throw new InvokeException();
		}
		return (List) Json.fromJson(info);
	}
}
