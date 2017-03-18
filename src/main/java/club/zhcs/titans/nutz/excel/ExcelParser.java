package club.zhcs.titans.nutz.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nutz.lang.Times;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * excel解析器
 *
 * @author JiangKun
 * @date 2016年12月20日 下午2:47:12
 */
public class ExcelParser {

    @SuppressWarnings("deprecation")
    public static List<List<String>> parseExcel(InputStream in) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(in);
            List<List<String>> records = new ArrayList<List<String>>();
            Sheet sheet = workbook.getSheetAt(0); // 遍历第一个Sheet

            //第一行头部的cell数量为每一行默认数量
            Row topRow = sheet.getRow(sheet.getTopRow());
            Iterator<Cell> it = topRow.cellIterator();
            int headLength = 0;
            while (it.hasNext()) {
                //头部为空cell为止
                if (it.next() == null)
                    break;
                headLength++;
            }

            //遍历每一列
            for (Row row : sheet) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < headLength; i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = null;
                    if (cell == null) {
                        cellValue = "";
                        list.add(cellValue);
                        continue;
                    }
                    int cellType = cell.getCellType();
                    if (cellType == CellType.STRING.getCode()) // 文本
                        cellValue = cell.getRichStringCellValue().getString();
                    else if (cellType == CellType.NUMERIC.getCode()) { // 数字、日期
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellValue = Times.format("yyyy-MM-dd HH:mm:ss", cell.getDateCellValue());
                        } else {
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }
                    } else if (cellType == CellType.BOOLEAN.getCode()) {// 布尔型
                        cellValue = String.valueOf(cell.getBooleanCellValue());
                    } else if (cellType == CellType.BLANK.getCode()) { // 空白
                        cellValue = "";
                    } else if (cellType == CellType.ERROR.getCode()) { // 错误
                        cellValue = "ERROR";
                    } else
                        cellValue = "";
                    list.add(cellValue);
                }
                records.add(list);
            }
            return records;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null)
                    workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
