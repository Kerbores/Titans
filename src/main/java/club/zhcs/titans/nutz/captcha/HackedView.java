package club.zhcs.titans.nutz.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.lang.Lang;
import org.nutz.lang.Maths;
import org.nutz.lang.random.R;
import org.nutz.mvc.View;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月5日 下午4:37:03
 */
public class HackedView implements View {
	public static void main(String[] args) {
		UserAgent ua = new UserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2774.3 Safari/537.36");
		System.err.println(ua.getOperatingSystem().getName());
		System.err.println(ua.getBrowser().getName() + " " + ua.getBrowserVersion().getVersion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nutz.mvc.View#render(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Throwable {
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		UserAgent ua = new UserAgent(req.getHeader("user-agent"));

		String msg = String.format("你是全球第%d位被攻击的用户", R.random(1000, 5000000));
		String ip = "IP地址:" + Lang.getIP(req);
		String os = "操作系统:" + ua.getOperatingSystem().getName();
		String br = "浏览器:" + ua.getBrowser().getName() + " " + ua.getBrowserVersion().getVersion();

		int length = Maths.max(msg.length(), ip.length(), os.length(), br.length());

		int width = 30 * length + 20, height = 35 * 4;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();

		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		g.setFont(new Font(null, Font.BOLD, 25));

		for (int i = 0; i < msg.length(); i++) {
			String rand = msg.charAt(i) + "";
			// 将认证码显示到图象中
			g.setColor(getRandColor(20, 130));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 30 * i + 10, 25 + getRandInt(-2, 2));
		}

		for (int i = 0; i < ip.length(); i++) {
			String rand = ip.charAt(i) + "";
			// 将认证码显示到图象中
			g.setColor(getRandColor(20, 130));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 30 * i + 10, 60 + getRandInt(-2, 2));
		}

		for (int i = 0; i < os.length(); i++) {
			String rand = os.charAt(i) + "";
			// 将认证码显示到图象中
			g.setColor(getRandColor(20, 130));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 30 * i + 10, 95 + getRandInt(-2, 2));
		}

		for (int i = 0; i < br.length(); i++) {
			String rand = br.charAt(i) + "";
			// 将认证码显示到图象中
			g.setColor(getRandColor(20, 130));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.drawString(rand, 30 * i + 10, 130 + getRandInt(-2, 2));
		}
		g.dispose();

		OutputStream out = resp.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.flush();
		out.close();
	}

	public Color getRandColor(int b, int e) {// 给定范围获得随机颜色
		if (b > 255)
			b = 255;
		if (e > 255)
			e = 255;
		int rc = getRandInt(b, e);
		int gc = getRandInt(b, e);
		int bc = getRandInt(b, e);
		return new Color(rc, gc, bc);
	}

	public int getRandInt(int b, int e) {
		if (b > e) {
			int temp = e;
			e = b;
			b = temp;
		}
		Random random = new Random();
		return b + random.nextInt(e - b);
	}
}
