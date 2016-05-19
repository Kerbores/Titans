package club.zhcs.jxls;

import java.util.List;

import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import club.zhcs.jxls.entity.Row;
import club.zhcs.jxls.entity.Title;

public class Sheets {
	public static WritableSheet createSheet(WritableWorkbook excel, String name, int index) {
		return excel.createSheet(name, index);
	}

	public static boolean addRow(WritableSheet sheet, Row row) {
		for (WritableCell lable : row.getLabels()) {
			try {
				sheet.addCell(lable);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean addRows(WritableSheet sheet, List<Row> rows) {
		for (Row row : rows) {
			if (!addRow(sheet, row)) {
				return false;
			}
		}
		return true;
	}

	public static boolean addTitle(WritableSheet sheet, Title title) {
		try {
			sheet.mergeCells(title.getStart().getX(), title.getStart().getY(), title.getStart().getX() + title.getColumn(), title.getStart().getY() + title.getRow());
			Label label = new Label(title.getStart().getX(), title.getStart().getY(), title.getTitle(), title.getFormat());
			sheet.addCell(label);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}