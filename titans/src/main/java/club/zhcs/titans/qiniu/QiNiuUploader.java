package club.zhcs.titans.qiniu;

import java.io.File;

import org.nutz.lang.Files;
import org.nutz.lang.random.R;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛的文件上传工具
 * 
 * @author 王贵源
 * @email kerbores@kerbores.com
 *
 *        create at 2015年10月22日 上午8:26:42
 */
public class QiNiuUploader {
	/**
	 * accessKey
	 */
	private String accessKey;
	/**
	 * secretKey
	 */
	private String secretKey;
	/**
	 * 资源 bucket
	 */
	private String bucket;

	/**
	 * 七牛资源域名
	 */
	private String domain;

	protected Auth auth;

	protected UploadManager uploadManager = new UploadManager();

	/**
	 * 无参数构造器
	 */
	public QiNiuUploader() {
		super();
	}

	/**
	 * 全参构造器
	 * 
	 * @param accessKey
	 * @param secretKey
	 * @param bucket
	 * @param domain
	 */
	public QiNiuUploader(String accessKey, String secretKey, String bucket, String domain) {
		super();
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.bucket = bucket;
		this.domain = domain;
	}

	/**
	 * @return the accessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * @return the bucket
	 */
	public String getBucket() {
		return bucket;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 七牛资源key的生成规则,目前是UUID的可以覆盖此方法实现自己的key规则
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	protected String getFileName(String path) {
		return R.UU16() + "." + Files.getSuffixName(new File(path));
	}

	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * 七牛的上传令牌生成,可以覆盖
	 * 
	 * @return
	 */
	protected String getUpToken() {
		auth = Auth.create(accessKey, secretKey);
		return auth.uploadToken(bucket, null, 3600,
				new StringMap().putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
	}

	/**
	 * 根据资源key获取资源访问地址
	 * 
	 * @param key
	 * @return
	 */
	public String getDownloadUrl(String key, boolean publicFlag) {
		return publicFlag ? domain + key : Auth.create(accessKey, secretKey).privateDownloadUrl(domain + key);
	}

	/**
	 * 自由资源访问地址
	 * 
	 * @param key
	 * @return
	 */
	public String privateUrl(String key) {
		return getDownloadUrl(key, false);
	}

	/**
	 * 公有资源访问地址
	 * 
	 * @param key
	 * @return
	 */
	public String publicUrl(String key) {
		return getDownloadUrl(key, true);
	}

	/**
	 * @param accessKey
	 *            the accessKey to set
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * @param bucket
	 *            the bucket to set
	 */
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件
	 * @return
	 */
	public String upload(File file) {
		return upload(file.getPath());
	}

	/**
	 * 根据路径上传文件
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public String upload(String path) {
		return upload(path, getFileName(path));
	}

	public String upload(byte[] data) {
		String key = R.UU16();
		try {
			Response res = uploadManager.put(data, key, getUpToken());
			if (res.isOK()) {
				return key;
			} else {
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 上传文件
	 * 
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名称,建议用UUID生成
	 * @return 七牛文件key 将此key存入本地数据库 使用时可通过 七牛域名/${key}访问文件
	 */
	public String upload(String path, String fileName) {
		try {
			Response res = uploadManager.put(new File(path), fileName, getUpToken());
			if (res.isOK()) {
				return fileName;
			} else {
			}
		} catch (QiniuException e) {
			e.printStackTrace();
		}
		return null;
	}

}
