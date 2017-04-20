package club.zhcs.titans.nutz.captcha;

import org.nutz.lang.random.R;

public class DefaultCaptchaGener implements CaptchaGener {
	
	public static final String NUMBER_POOL = "0123456789";
	
	public static final String LETTER_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	public static final String DEFAULT_POOL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private String pool;

	public DefaultCaptchaGener() {
		super();
		pool = DEFAULT_POOL;
	}

	public DefaultCaptchaGener(String pool) {
		super();
		this.pool = pool;
	}

	@Override
	public String gen(int length) {
		if (length <= 0) {
			return "";
		}
		char[] pools = pool.toCharArray();
		String target = "";
		while (target.length() < length) {
			target += pools[R.random(0, pools.length - 1)];
		}
		return target;
	}

}
