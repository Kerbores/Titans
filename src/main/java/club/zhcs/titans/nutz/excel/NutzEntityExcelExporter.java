package club.zhcs.titans.nutz.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Times;
import org.nutz.lang.eject.Ejecting;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * 从Entity列表导出
 * @author JiangKun
 * @date 2016年11月29日 下午4:24:14
 */
public class NutzEntityExcelExporter<T extends Entity> extends AbstractNutzExcelExporter implements EntityExcelExporter<T>{
	
	private List<T> records;
	private List<String> headers;
	private List<Ejecting> ejectings;
	
	public void exportEntityList(List<T> records, OutputStream out, String title) {
		exportEntityList(records, out, title, DEFAULT_SHEET_NAME);
	}

	public void exportEntityList(List<T> records, OutputStream out, String title, String sheetName) {
		exportEntityList(records, out, title, sheetName, null);
	}
	
	public void exportEntityList(List<T> records, OutputStream out, String title, String sheetName, Map<String, String> fieldsMappings) {

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

		List<String> headers = new ArrayList<String>();// 表头字段

		Mirror<?> mirror = Mirror.me(records.get(0).getClass());
		Field[] fields = null;
		List<Ejecting> ejectings = new ArrayList<Ejecting>();// 反射取值器

		if (fieldsMappings != null) {// 用户定义映射字段作为列
			Set<String> fieldSet = fieldsMappings.keySet();
			List<Field> fieldList = new ArrayList<Field>();
			for (String field : fieldSet) {
				try {
					fieldList.add(mirror.getField(field));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
			}

			headers = Lang.collection2list(fieldsMappings.values());
			fields = Lang.collection2array(fieldList);

		} else {// 默认反射取所有@Column注解的字段作为列
			fields = mirror.getFields(Column.class);
			for (Field field : fields) {
				Comment comment = field.getAnnotation(Comment.class);
				headers.add(comment.value());
			}
		}

		for (Field field : fields) {
			Ejecting ej = mirror.getEjecting(field);
			ejectings.add(ej);
		}

		this.records = records;
		this.headers = headers;
		this.ejectings = ejectings;
		 
		Workbook workbook = createWorkbook(title, sheetName);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
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
			HSSFCellStyle style = this.getColumnDataStyle(workbook); // 单元格样式对象

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
				T obj = records.get(i);// 遍历每个数据对象
				HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数

				for (int j = 0; j < ejectings.size(); j++) {
					Object value = ejectings.get(j).eject(obj);
					HSSFCell cell = null;
					if (value instanceof Date) {
						cell = row.createCell(j, CellType.STRING); 
						cell.setCellValue(Times.format("yyyy-MM-dd HH:mm:ss", (Date) value));
					} else if (value instanceof Double) {
						cell = row.createCell(j, CellType.NUMERIC); 
						cell.setCellValue((Double)value);
					} else if (value instanceof BigDecimal) {
						cell = row.createCell(j, CellType.NUMERIC); 
						BigDecimal bd = (BigDecimal)value;
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
	
	public List<T> getRecords() {
		return records;
	}
	
	public void setRecords(List<T> records) {
		this.records = records;
	}
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<Ejecting> getEjectings() {
		return ejectings;
	}

	public void setEjectings(List<Ejecting> ejectings) {
		this.ejectings = ejectings;
	}

}
