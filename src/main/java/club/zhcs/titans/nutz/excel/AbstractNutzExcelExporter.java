package club.zhcs.titans.nutz.excel;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author JiangKun
 * @date 2016年11月29日 下午4:15:51
 */
public abstract class AbstractNutzExcelExporter implements ExcelExporter{
	
	Log log = Logs.get();
	

	public static final String DEFAULT_SHEET_NAME = "Sheet1";
	
	public static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#.00");  
	
	protected List<String> headers;
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	protected abstract Workbook createWorkbook(String title, String sheetName);

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnHeaderStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBold(true);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getColumnDataStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}
	
	
	/**
	 * 创建空的workbook
	 * @return
	 */
	public Workbook createEmptyWorkbook() {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
		HSSFSheet sheet = workbook.createSheet(DEFAULT_SHEET_NAME); // 创建工作表

		// 产生表格标题行
		HSSFRow rowm = sheet.createRow(0);
		HSSFCell cellTiltle = rowm.createCell(0);

		// sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面 - 可扩展】
		HSSFCellStyle columnTopStyle = this.getColumnHeaderStyle(workbook);// 获取列头样式对象
		
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (10 - 1)));
		cellTiltle.setCellStyle(columnTopStyle);
		cellTiltle.setCellValue("无数据!");
		
		return workbook;
	}
}
