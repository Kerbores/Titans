package club.zhcs.titans.nutz.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.nutz.dao.entity.Record;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;

/**
 * 从Nutz的Record列表导出 
 * author Jiangkun 
 * created on 2016年11月20日
 */
public class NutzRecordExcelExporter extends AbstractNutzExcelExporter implements RecordExcelExporter{

	private List<Record> records;

	// =============从record导出======================
	public void exportRecordList(List<Record> records, OutputStream out, String title) {
		exportRecordList(records, out, title, DEFAULT_SHEET_NAME);
	}

	public void exportRecordList(List<Record> records, OutputStream out, String title, String sheetName) {
		exportRecordList(records, out, title, sheetName, null);
	}

	public void exportRecordList(List<Record> records, OutputStream out, String title, String sheetName, List<String> columnHeaders) {
		if (records == null || records.size() == 0) {
			log.warn("No data found to export!");
			Workbook workbook = createEmptyWorkbook();
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		this.records = records;

		if (columnHeaders != null) {
			this.headers = columnHeaders;
		} else {
			Record record = records.get(0);// 取一条先拿表头字段
			this.headers = Lang.collection2list(record.keySet());// 表头字段直接从数据库别名获取
		}

		Workbook workbook = createWorkbook(title, sheetName);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// =============从record导出======================

	/**
	 * 根据title和sheetName创建excel workbook
	 */
	@SuppressWarnings("deprecation")
	protected Workbook createWorkbook(String title, String sheetName) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet(sheetName); // 创建工作表

			// 产生表格标题行
			HSSFRow rowm = sheet.createRow(0);
			HSSFCell cellTiltle = rowm.createCell(0);

			// sheet样式定义
			HSSFCellStyle columnTopStyle = this.getColumnHeaderStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getColumnDataStyle(workbook); // 数据单元格样式对象

			sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (headers.size() - 1)));
			cellTiltle.setCellStyle(columnTopStyle);
			cellTiltle.setCellValue(title);

			// 定义所需列数
			int columnNum = headers.size();
			// 数据行数
			int recordNum = records.size();

			HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)

			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(headers.get(n));
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}

			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < recordNum; i++) {
				Record obj = records.get(i);// 遍历每个数据对象
				HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数

				for (int j = 0; j < columnNum; j++) {
					Object value = obj.get(headers.get(j));
					HSSFCell cell = null;
					if (value instanceof Date) {
						cell = row.createCell(j, CellType.STRING);
						cell.setCellValue(Times.format("yyyy-MM-dd HH:mm:ss", (Date) value));
					} else if (value instanceof Double) {
						cell = row.createCell(j, CellType.NUMERIC);
						cell.setCellValue((Double) value);
					} else if (value instanceof BigDecimal) {
						cell = row.createCell(j, CellType.NUMERIC);
						BigDecimal bd = (BigDecimal) value;
						cell.setCellValue(bd.doubleValue());
					} else {
						cell = row.createCell(j, CellType.STRING);
						cell.setCellValue(value == null ? "" : value.toString());
					}
					cell.setCellStyle(style); // 设置单元格样式
				}
			}

			// 让列宽随着导出的列长自动适应
			for (int colNum = 0; colNum < columnNum; colNum++) {
				int columnWidth = sheet.getColumnWidth(colNum) / 256;
				for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
					HSSFRow currentRow;
					// 当前行未被使用过
					if (sheet.getRow(rowNum) == null) {
						currentRow = sheet.createRow(rowNum);
					} else {
						currentRow = sheet.getRow(rowNum);
					}
					if (currentRow.getCell(colNum) != null) {
						HSSFCell currentCell = currentRow.getCell(colNum);
						if (CellType.STRING.getCode() == currentCell.getCellType()) {
							int length = currentCell.getStringCellValue().getBytes().length;
							if (columnWidth < length) {
								columnWidth = length;
							}
						}
					}
				}
				if (colNum == 0) {
					sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
				} else {
					sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
				}
			}

			return workbook;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

}
