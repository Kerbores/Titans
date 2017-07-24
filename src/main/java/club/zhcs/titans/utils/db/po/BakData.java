package club.zhcs.titans.utils.db.po;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;

@Table("kerbores_bak_data")
@Comment("已删除数据的备份")
public class BakData extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column("d_table_name")
	@Comment("数据表名")
	private String table;

	@Column("d_entity_full_name")
	@Comment("数据实体全限定名")
	private String entityFullName;

	@Column("d_data_info")
	@Comment("数据的json串")
	@ColDefine(type = ColType.TEXT)
	private String data;

	@Column("d_del_date")
	@Comment("数据的删除时间,即本记录的产生时间")
	private Date deleteDate = Times.now();

	public BakData() {
	}

	public BakData(Entity entity) {
		this.entityFullName = entity.getClass().getName();
		this.table = entity.getClass().getAnnotation(Table.class).value();
		this.data = Json.toJson(entity, JsonFormat.compact());
	}

	public String getTable() {
		return table;
	}

	public NutMap getDataMap() {
		return Lang.map(data);
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getEntityFullName() {
		return entityFullName;
	}

	public void setEntityFullName(String entityFullName) {
		this.entityFullName = entityFullName;
	}

	public String getData() {
		return Json.toJson(getDataMap(), JsonFormat.nice());
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

}
