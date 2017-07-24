package club.zhcs.titans.utils.db;

import java.io.File;

import org.nutz.lang.Files;

/**
 * SQL语句格式化
 * 
 * @author Kerbores
 * 
 */
public class SQLFormater {

	/**
	 * 格式sql
	 * 
	 * @param sqlFile
	 *            待格式化的sql语句文件
	 * @return sql语句
	 */
	public static String formatSqlFile(File sqlFile) {
		return Files.read(sqlFile).replaceAll(System.getProperty("line.separator"), " ").replaceAll("\\s{2,}", " ");
	}

}
