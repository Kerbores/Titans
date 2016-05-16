package club.zhcs.titans.utils.common;

import java.util.ArrayList;
import java.util.List;

import org.nutz.castor.Castors;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.Strings;

/**
 * @author Ixion
 * 
 */
public class StringUtils {
	/**
	 * 字符串去除空白字符后从左到右按照一定位数插入字符
	 * 
	 * @param str
	 *            源
	 * @param n
	 *            位数
	 * @param sp
	 *            插入字符
	 * @return
	 */
	public static String change(String str, int n, String sp) {
		String info = "";
		String nstr = sTrim(str);
		for (int i = 0; i < nstr.length(); i++) {
			info += nstr.charAt(i);
			if (i > 0 && (i + 1) % n == 0) {
				info += sp;
			}
		}
		return info;
	}

	public static String getString(Object obj) {
		return isNullOrEmpty(obj) ? "" : obj.toString();
	}

	public static boolean isNullOrEmpty(Object object) {
		return object == null ? true : Strings.isBlank(object.toString());
	}

	/**
	 * 字符串去除空白字符后从右到左按照一定位数插入字符
	 * 
	 * @param str
	 *            源
	 * @param n
	 *            位数
	 * @param sp
	 *            插入字符
	 * @return
	 */
	public static String rChange(String str, int n, String sp) {
		String info = "";
		String nstr = sTrim(str);
		for (int i = nstr.length() - 1; i >= 0; i--) {
			info = nstr.charAt(i) + info;
			if ((nstr.length() - i) % n == 0) {
				info = sp + info;
			}
		}
		return info;
	}

	/**
	 * 去除全部空白
	 * 
	 * @param in
	 * @return
	 */
	public static String sTrim(String in) {
		return in.replaceAll("\\s*", "");
	}

	/**
	 * 将数字格式字符串转换成list
	 * 
	 * @param source
	 *            源字符串，格式为1,2,3
	 * @return
	 */
	public static List<Integer> stringConvertList(String source) {
		return stringConvertList(source, Integer.class);
	}

	public static <T> List<T> stringConvertList(String[] source, final Class<T> clazz) {
		final List<T> target = new ArrayList<T>();
		Lang.each(source, new Each<String>() {

			@Override
			public void invoke(int index, String info, int length) throws ExitLoop, ContinueLoop, LoopException {
				target.add(Castors.me().castTo(info, clazz));
			}
		});
		return target;
	}

	public static <T> List<T> stringConvertList(String source, Class<T> clazz) {
		if (Strings.isBlank(source)) {
			return new ArrayList<T>();
		}
		String[] infos = source.split(",");
		return stringConvertList(infos, clazz);
	}
}
