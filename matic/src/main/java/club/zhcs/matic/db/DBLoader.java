package club.zhcs.matic.db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.nutz.dao.DB;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.impl.SimpleDataSource;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 数据库加载器
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年7月5日 下午1:30:30
 */
public class DBLoader {

	/**
	 * 获取dao对象
	 * 
	 * @param db
	 * @return
	 */
	public static Dao dao(DBProperties db) {
		SimpleDataSource dataSource = new SimpleDataSource();
		dataSource.setJdbcUrl(db.getJDBCUrl());
		dataSource.setUsername(db.getUser());
		dataSource.setPassword(db.getPassword());
		return new NutDao(dataSource);
	}

	/**
	 * 获取表名列表
	 * 
	 * @param db
	 * @return
	 */
	public static List<Record> tables(DBProperties db) {
		String sql_ = null;
		switch (db.getType()) {
		case MYSQL:
			sql_ = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = @schame";
			break;

		default:
			break;
		}
		Sql sql = Sqls.create(sql_);
		sql.params().set("schame", db.getSchame());
		sql.setCallback(Sqls.callback.records());
		dao(db).execute(sql);

		return sql.getList(Record.class);
	}

	/**
	 * 获取表的结构描述
	 * 
	 * @param dao
	 * @param table
	 * @return
	 */
	public static List<Record> columns(Dao dao, String table) {

		String sql_ = null;
		switch (dao.meta().getType()) {
		case MYSQL:
			sql_ = "select * from INFORMATION_SCHEMA.columns where table_name= @table";
			break;

		default:
			break;
		}
		Sql sql = Sqls.create(sql_);
		sql.params().set("table", table);
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		return sql.getList(Record.class);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		DBProperties db = new DBProperties();
		db.setType(DB.MYSQL);
		db.setDbAddress("127.0.0.1");
		db.setPort(3306);
		db.setSchame("tdb");
		db.setUser("root");
		db.setPassword("123456");
		System.err.println(db.getJDBCUrl());

		final Dao dao = dao(db);
		System.setErr(new PrintStream(Files.createFileIfNoExists("log.txt")));

		Lang.each(tables(db), new Each<Record>() {

			@Override
			public void invoke(int index, Record ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				System.err.println("-------------Table name---------------> " + ele.getString("table_name"));
				System.err.println(DBLoader.columns(dao, ele.getString("table_name")));
			}
		});
	}

}
