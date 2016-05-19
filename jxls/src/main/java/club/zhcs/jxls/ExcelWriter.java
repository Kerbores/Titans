package club.zhcs.jxls;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.nutz.lang.Files;

import club.zhcs.jxls.entity.Row;

/**
 * excel写入工具
 * 
 * @author wkipy
 *
 */
public class ExcelWriter {

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param sheetName
	 * @param data
	 * @param type
	 * @return
	 */
	public static boolean write(File file, String sheetName, List data, Class type) {
		WritableWorkbook excel = Excels.createExcel(file);
		WritableSheet sheet = Sheets.createSheet(excel, sheetName, 0);
		if (type.isAssignableFrom(Map.class)) {
			Sheets.addRows(sheet, Rows.genRows(data, 0));
		} else {
			Row subTitle = Titles.createSubTitle(0, type);
			Sheets.addRow(sheet, subTitle);
			Sheets.addRows(sheet, Rows.genRows(data, 1));
		}
		return Excels.write(excel);
	}

	public static boolean write(OutputStream out, String sheetName, List data, Class type) {
		WritableWorkbook excel = Excels.createExcel(out);
		WritableSheet sheet = Sheets.createSheet(excel, sheetName, 0);
		if (type.isAssignableFrom(Map.class)) {
			Sheets.addRows(sheet, Rows.genRows(data, 0));
		} else {
			Row subTitle = Titles.createSubTitle(0, type);
			Sheets.addRow(sheet, subTitle);
			Sheets.addRows(sheet, Rows.genRows(data, 1));
		}
		return Excels.write(excel);
	}

	/**
	 * 写入文件
	 * 
	 * @param path
	 * @param sheetName
	 * @param data
	 * @param type
	 * @return
	 */
	public static boolean write(String path, String sheetName, List data, Class type) {
		return write(Files.createFileIfNoExists2(path), sheetName, data, type);
	}
}
