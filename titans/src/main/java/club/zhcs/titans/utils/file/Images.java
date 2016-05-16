package club.zhcs.titans.utils.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.nutz.filepool.NutFilePool;
import org.nutz.http.Header;
import org.nutz.http.Request;
import org.nutz.http.Request.METHOD;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.http.sender.PostSender;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.repo.Base64;

import club.zhcs.titans.utils.net.Downloader;

/**
 * 图片base64处理
 * 
 * @author Kerbores
 * 
 */
public class Images extends org.nutz.img.Images {
	static NutFilePool pool = new NutFilePool(System.getProperty("java.io.tmpdir"), 3000);// 创建一个临时文件池

	/**
	 * 图片缩放裁剪
	 * 
	 * @param src
	 *            源图
	 * @param scale
	 *            缩放比例
	 * @param startX
	 *            起点X坐标
	 * @param startY
	 *            起点Y坐标
	 * @param endX
	 *            终点X坐标
	 * @param endY
	 *            终点Y坐标
	 * @return 目标图片
	 * 
	 * @throws IOException
	 *             当读写文件失败时抛出
	 */
	public static BufferedImage zoomAndClip(File src, double scale, int startX, int startY, int endX, int endY) throws IOException {
		BufferedImage bfi = read(src);
		bfi = zoomScale(bfi, (int) (bfi.getWidth() * scale), (int) (bfi.getHeight() * scale));// 等比缩放
		File f = pool.createFile("." + Files.getSuffixName(src));
		write(bfi, f);// 写入
		bfi = clipScale(f.getPath(), f.getPath(), new int[] { startX, startY }, new int[] { endX, endY });// 裁剪
		return read(f);
	}

	/**
	 * 
	 * @param src
	 * @param scale
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage zoomAndClip(File src, double scale, Point start, Point end) throws IOException {
		return zoomAndClip(src, scale, start.getX(), start.getY(), end.getX(), end.getY());
	}

	/**
	 * 将 BufferedImage 写入文件
	 * 
	 * @param bfi
	 *            BufferedImage图像
	 * @return 写入的文件对象
	 */
	public static File toFile(BufferedImage bfi) {
		File f = pool.createFile(".jpg");
		writeJpeg(bfi, f, 1.0f);
		return f;
	}

	/**
	 * 
	 * @param src
	 * @param scale
	 * @param start
	 * @param w
	 * @param h
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage zoomAndClip(File src, double scale, Point start, int w, int h) throws IOException {
		return zoomAndClip(src, scale, start.getX(), start.getY(), start.getX() + w, start.getY() + h);
	}

	public static BufferedImage zoomAndClip(String srcPath, double scale, Point start, int w, int h) throws Exception {
		Downloader downloader = new Downloader();
		File f = null;
		downloader.setUrlAndFile(new URL(srcPath), f = pool.createFile(".png"));
		f = downloader.call();
		return zoomAndClip(f, scale, start, w, h);
	}

	/**
	 * 坐标点
	 * 
	 * @author wkipy
	 *
	 */
	public static class Point {
		/**
		 * x坐标
		 */
		private int x;
		/**
		 * y坐标
		 */
		private int y;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public Point(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Point(Number x, Number y) {
			super();
			this.x = x.intValue();
			this.y = y.intValue();
		}

		public Point() {
			super();
		}

	}

	/**
	 * 
	 * @param imgStr
	 *            图片数据
	 * @param outFile
	 *            输出文件
	 * @return
	 */
	public static File GenerateImage(String imgStr, File outFile) {
		if (imgStr == null) // 图像数据为空
			return null;
		try {
			// Base64解码
			byte[] b = Base64.decode(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(outFile);
			out.write(b);
			out.flush();
			out.close();
			return outFile;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据base64串生成文件
	 * 
	 * @param imgStr
	 *            base64串
	 * @param outDir
	 *            输出目录
	 * @param suffix
	 *            扩展名
	 * @return
	 */
	public static File GenerateImage(String imgStr, File outDir, String suffix) {
		File file = new File(outDir.getPath() + File.separator + System.nanoTime() + "." + suffix);
		return GenerateImage(imgStr, file);
	}

	/**
	 * 生成png图片
	 * 
	 * @param imgStr
	 *            base64 image信息串
	 * @return 图片文件
	 */
	public static File GeneratePngImage(String imgStr) {// 对字节数组字符串进行Base64解码并生成图片
		return GenerateImage(imgStr, new File(System.nanoTime() + ".png"));
	}

	/**
	 * 图片转换成base64串
	 * 
	 * @param imgFile
	 *            图片文件
	 * @return base64 图片信息串
	 */
	public static String GetImageStr(File imgFile) {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		// 对字节数组Base64编码
		return Base64.encodeToString(data, false);
	}

	public static NutMap baiduOcr(String path, String key) {
		NutMap params = NutMap.NEW();
		Map<String, String> header = new HashMap<String, String>();
		header.put("apikey", key);

		params.put("fromdevice", "pc");
		params.put("clientip", "10.0.1.1");
		params.put("detecttype", "LocateRecognize");
		params.put("languagetype", "CHN_ENG");
		params.put("imagetype", "1");
		params.put("image", GetImageStr(new File(path)));

		Request request = Request.create("http://apis.baidu.com/apistore/idlocr/ocr", METHOD.POST, params, Header.create(header));
		Sender sender = PostSender.create(request);
		Response response = sender.send();
		String info = response.getContent();

		return Lang.map(info);
	}

	private static final Log log = Logs.get();
}
