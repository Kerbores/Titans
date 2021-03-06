package club.zhcs.titans.nutz.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * XSS处理原生request
 * @author JiangKun
 * @date 2016年6月1日
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return cleanXSS(value);
	}
	
	private String cleanXSS(String value) {
		value = value.replaceAll("<", "&lt;")
		.replaceAll(">", "&gt;")
		.replaceAll("\\(", "&#40;")
		.replaceAll("\\)", "&#41;")
		.replaceAll("'", "&#39;")
		;
		return value;
	}
}