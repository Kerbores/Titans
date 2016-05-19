package club.zhcs.jxls;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

public class Excels {
	public static WritableWorkbook createExcel(File file) {
		WritableWorkbook target = null;
		file.setWritable(true);
		try {
			target = Workbook.createWorkbook(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static WritableWorkbook createExcel(String filePath) {
		return createExcel(new File(filePath));
	}

	public static WritableWorkbook createExcel(OutputStream out) {
		WritableWorkbook target = null;
		try {
			target = Workbook.createWorkbook(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static Workbook getWorkbook(String path) {
		Workbook book = null;
		try {
			File file = new File(path);
			file.setReadOnly();
			book = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}

	public static boolean write(WritableWorkbook excel) {
		try {
			excel.write();
			excel.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}