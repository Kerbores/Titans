package club.zhcs.jxls.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

import club.zhcs.jxls.FieldMapper;
import club.zhcs.jxls.FieldMapper.MapperNode;

public class Row {
	private List<WritableCell> labels;
	private int rNum;
	private int cellSize;

	@Override
	public String toString() {
		return "Row [labels=" + this.labels + ", rNum=" + this.rNum + ", cellSize=" + this.cellSize + "]";
	}

	public int getrNum() {
		return this.rNum;
	}

	public void setrNum(int rNum) {
		this.rNum = rNum;
	}

	public int getCellSize() {
		return this.cellSize;
	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public List<WritableCell> getLabels() {
		return this.labels;
	}

	public Row(int rNum, int cellSize) {
		this.rNum = rNum;
		this.cellSize = cellSize;
	}

	public Row(int rNum, List list) {
		this.rNum = rNum;
		this.cellSize = list.size();
		List ls = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			ls.add(createLabel(i, rNum, list.get(i)));
		}
		this.labels = ls;
	}

	public Row(int rNum, Map map) {
		this.rNum = rNum;
		this.cellSize = map.size();
		List ls = new ArrayList();
		Iterator it = map.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			ls.add(createLabel(i, rNum, map.get(it.next())));
			i++;
		}
		this.labels = ls;
	}

	public Row(int rNum, Object obj, FieldMapper mapper) {
		this(rNum, toList(obj, mapper));
	}

	private static WritableCell createLabel(int col, int row, Object data) {
		if ((data instanceof Double))
			return new Number(col, row, ((Double) data).doubleValue());
		if ((data instanceof Float))
			return new Number(col, row, ((Float) data).floatValue());
		if ((data instanceof Integer))
			return new Number(col, row, ((Integer) data).intValue());
		if ((data instanceof java.lang.Boolean))
			return new jxl.write.Boolean(col, row, ((java.lang.Boolean) data).booleanValue());
		if ((data instanceof Date)) {
			return new DateTime(col, row, (Date) data);
		}
		return new Label(col, row, null == data ? "" : data.toString());
	}

	private static List toList(Object obj, FieldMapper mapper) {
		List list = new ArrayList();
		NutMap temp = Lang.map(Json.toJson(obj));
		for (MapperNode node : mapper.getNodes()) {
			list.add(temp.get(node.getField()));
		}
		return list;
	}
}
