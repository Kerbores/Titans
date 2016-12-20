package club.zhcs.titans.utils.net;

import java.io.File;
import java.io.IOException;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Files;
import org.nutz.lang.Streams;

public class NutzDownloader {

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            地址
	 * @param file
	 *            本地保存文件
	 * @param timeout
	 *            超时
	 * @return 下载得到的文件
	 * @throws IOException
	 */
	public static File downloan(String url, File file, int timeout) throws IOException {
		Response response = Http.get(url, timeout);
		if (response.isOK()) {
			Streams.write(Streams.fileOut(file), response.getStream());
			return file;
		}
		return null;
	}

	/**
	 * 
	 * @param url
	 *            地址
	 * @param file
	 *            文件路径
	 * @param timeout
	 *            超时
	 * @return 下载得到的文件
	 * @throws IOException
	 */
	public static File downloan(String url, String file, int timeout) throws IOException {
		return downloan(url, Files.createFileIfNoExists(file), timeout);
	}

}
