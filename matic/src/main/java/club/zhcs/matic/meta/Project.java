package club.zhcs.matic.meta;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 项目
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月5日 下午2:31:51
 */
public class Project extends Meta {
	/**
	 * 项目的名字
	 */
	private String name;
	/**
	 * 顶层package名
	 */
	private String packageName;
	/**
	 * 输出路径名
	 */
	private String output;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output
	 *            the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}

}
