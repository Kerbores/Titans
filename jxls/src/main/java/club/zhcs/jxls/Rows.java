package club.zhcs.jxls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import club.zhcs.jxls.entity.Row;

public class Rows {

	public static List<Row> genRows(List list, int startRowNumber) {
		if (list == null || list.size() == 0) {
			return null;
		}
		FieldMapper mapper = new FieldMapper(list.get(0));
		List rows = new ArrayList();
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Object obj = localIterator.next();
			Row row = new Row(startRowNumber, obj, mapper);
			rows.add(row);
			startRowNumber++;
		}
		return rows;
	}

	public static List<Row> genRowsFromMap(List<Map> list, FieldMapper mapper, int startRowNumber) {
		List rows = new ArrayList();
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Object obj = localIterator.next();
			Row row = new Row(startRowNumber, obj, mapper);
			rows.add(row);
			startRowNumber++;
		}
		return rows;
	}

}