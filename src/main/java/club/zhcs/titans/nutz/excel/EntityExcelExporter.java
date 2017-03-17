package club.zhcs.titans.nutz.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * Entity方式导出
 * @author JiangKun
 * @date 2016年11月29日 下午5:45:18
 */
public interface EntityExcelExporter<T extends Entity> extends ExcelExporter{

	public void exportEntityList(List<T> records, OutputStream out, String title);

	public void exportEntityList(List<T> records, OutputStream out, String title, String sheetName);
	
	public void exportEntityList(List<T> records, OutputStream out, String title, String sheetName, Map<String, String> fieldsMappings);
	
}
