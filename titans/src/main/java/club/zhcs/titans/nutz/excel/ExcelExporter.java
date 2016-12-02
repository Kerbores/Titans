package club.zhcs.titans.nutz.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author JiangKun
 * @date 2016年11月29日 下午4:14:48
 */
public interface ExcelExporter {

	//列头样式
	public HSSFCellStyle getColumnHeaderStyle(HSSFWorkbook workbook);
	
	//数据样式
	public HSSFCellStyle getColumnDataStyle(HSSFWorkbook workbook);
	
	//数据为空时的workbook
	public Workbook createEmptyWorkbook();
}
