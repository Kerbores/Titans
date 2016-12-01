package club.zhcs.titans.nutz.excel;

import java.io.OutputStream;
import java.util.List;

import org.nutz.dao.entity.Record;

/**
 * Record方式导出
 * @author JiangKun
 * @date 2016年11月29日 下午5:45:34
 */
public interface RecordExcelExporter extends ExcelExporter{

	public void exportRecordList(List<Record> records, OutputStream out, String title);
	
	public void exportRecordList(List<Record> records, OutputStream out, String title, String sheetName);

	public void exportRecordList(List<Record> records, OutputStream out, String title, String sheetName, List<String> columnHeaders);
}
