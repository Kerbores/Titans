package club.zhcs.jxls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import club.zhcs.jxls.entity.Row;

public class Rows {
	public static List<Row> genRows(List list, int startRowNumber) {
		List rows = new ArrayList();
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Object obj = localIterator.next();
			Row row = new Row(startRowNumber, obj);
			rows.add(row);
			startRowNumber++;
		}
		return rows;
	}
}