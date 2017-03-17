package club.zhcs.titans.utils.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;

/**
 * SQL运行器
 *
 * @author Kerbores
 */
public class SqlActuator {

	/**
	 * 执行报表或者查询类sql<br>
	 * 通过Sqls.creat(""),sql.vars().set(key,val)/sql.params().set(key,value)
	 * 可很方便的创建各种自定义的sql,详情见
	 * <a href='http://api.kerbores.com/nutz/dao/customized_sql.html'>NUTZ
	 * 自定义SQL</a>
	 *
	 * @param sql
	 *            sql接口对象
	 * @param dao
	 *            dao接口实例
	 * @return 记录列表
	 */
	public static List<Record> runReport(Sql sql, Dao dao) {
		sql.setCallback(Sqls.callback.records());
		dao.execute(sql);
		return sql.getList(Record.class);
	}

	public static List<Record> runReport(Sql sql, Dao dao, Pager pager) {
		sql.setCallback(Sqls.callback.records());
		sql.setPager(pager);
		dao.execute(sql);
		return sql.getList(Record.class);
	}

	public static club.zhcs.titans.utils.db.Pager<Record> runReportSql(Sql sql, Dao dao, Pager pager) {
		// 执行count
		Sql countSql = Sqls.create("SELECT COUNT(1) FROM (" + sql.toString() + ") as COUNTX");
		countSql.setCallback(Sqls.callback.integer());
		dao.execute(countSql);

		// 执行Pager
		sql.setCallback(Sqls.callback.records());
		sql.setPager(pager);
		dao.execute(sql);
		List<Record> list = sql.getList(Record.class);

		// 封装成web module 能直接返回的Pager
		club.zhcs.titans.utils.db.Pager<Record> webPager = new club.zhcs.titans.utils.db.Pager<Record>(pager.getPageSize(), pager.getPageNumber());
		webPager.setEntities(list);
		webPager.setCount(countSql.getInt());
		return webPager;
	}

	public static Record run(Sql sql, Dao dao) {
		sql.setCallback(Sqls.callback.record());
		dao.execute(sql);
		return sql.getObject(Record.class);
	}

	public static <T> T run(Sql sql, Dao dao, Class<T> classOfEntity) {
		sql.setCallback(Sqls.callback.entity());
		dao.execute(sql);
		return sql.getObject(classOfEntity);
	}

	public static <T> List<T> runsql(Sql sql, Dao dao, Class<T> classOfEntity) {
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao.getEntity(classOfEntity));
		dao.execute(sql);
		return sql.getList(classOfEntity);
	}

	public static <T> List<T> runsql(String query, Dao dao, Class<T> classOfEntity) {
		Sql sql = Sqls.create(query);
		return runsql(sql, dao, classOfEntity);
	}

	public static <T> List<T> runsql(String query, Dao dao, Class<T> classOfEntity, Pager pager) {
		Sql sql = Sqls.create(query);
		sql.setPager(pager);
		return runsql(sql, dao, classOfEntity);
	}

	public static <T> T getEntity(Sql sql, Dao dao, Class<T> classOfEntity) {
		sql.setCallback(Sqls.callback.entity());
		dao.execute(sql);
		return sql.getObject(classOfEntity);
	}

	public static <T> T getEntity(String query, Dao dao, Class<T> classOfEntity) {
		Sql sql = Sqls.create(query);
		return getEntity(sql, dao, classOfEntity);
	}

	/**
	 * 按照记录列表的形式返回数据
	 *
	 * @param query
	 *            查询语句
	 * @param dao
	 *            dao对象
	 * @return 记录列表
	 */
	public static List<Record> runReport(String query, Dao dao) {
		Sql sql = Sqls.create(query);
		return runReport(sql, dao);
	}

	/**
	 * 按照记录列表的形式返回数据
	 *
	 * @param query
	 *            查询语句
	 * @param dao
	 *            dao对象
	 * @param pager
	 *            分页对象
	 * 
	 * @return 记录列表
	 */
	public static List<Record> runReport(String query, Dao dao, Pager pager) {
		Sql sql = Sqls.create(query);
		sql.setPager(pager);
		return runReport(sql, dao);
	}

	/**
	 * 执行单列查询
	 *
	 * @param sql
	 *            sql对象
	 * @param dao
	 *            dao接口实例
	 * @return
	 */
	public static int runUnq(Sql sql, Dao dao) {
		sql.setCallback(Sqls.callback.integer());
		dao.execute(sql);
		return sql.getInt();
	}

	/**
	 * 执行查询单个结构的失去了语句 比如 count 查询对象id等int类型字段
	 *
	 * @param query
	 *            sql语句
	 * @param dao
	 *            dao接口对象
	 * @return 单结果的int形式
	 */
	public static int runUnq(String query, Dao dao) {
		Sql sql = Sqls.create(query);
		return runUnq(sql, dao);
	}

	/**
	 * 执行更新
	 *
	 * @param sql
	 *            sql接口对象
	 * @param dao
	 *            dao接口实例
	 * @return 影响的记录条数
	 */
	public static int runUpdate(Sql sql, Dao dao) {
		sql.setCallback(Sqls.callback.integer());
		dao.execute(sql);
		return sql.getUpdateCount();
	}

	/**
	 * 执行update语句
	 *
	 * @param query
	 *            sql语句
	 * @param dao
	 *            nutzdao对象
	 * @return 影响的记录条数
	 */
	public static int runUpdate(String query, Dao dao) {
		Sql sql = Sqls.create(query);
		return sql.getUpdateCount();
	}

	/**
	 * 执行Sql语句
	 *
	 * @param sql
	 *            语句
	 * @param dao
	 *            dao
	 */
	public static void runSql(String sql, Dao dao) {
		Sql sql_ = Sqls.create(sql);
		sql_.setCallback(new SqlCallback() {
			@Override
			public Object invoke(Connection connection, ResultSet rs, Sql sql) throws SQLException {
				return null;
			}
		});
		dao.execute(sql_);
	}

	/**
	 * 执行Sql语句
	 *
	 * @param sql
	 *            sql对象
	 * @param dao
	 *            dao
	 */
	public static void runSql(Sql sql, Dao dao) {
		sql.setCallback(new SqlCallback() {
			@Override
			public Object invoke(Connection connection, ResultSet rs, Sql sql) throws SQLException {
				return null;
			}
		});
		dao.execute(sql);
	}
}
