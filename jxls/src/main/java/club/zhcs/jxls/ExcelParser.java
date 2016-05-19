package club.zhcs.jxls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;

/**
 * excel读取工具
 * 
 * @author wkipy
 *
 */
public class ExcelParser {

	/**
	 * 从文件导出
	 * 
	 * @param path
	 * @param sheetName
	 * @param startRowNumber
	 * @param clazz
	 * @return
	 */
	public static List<Map> parse(String path, String sheetName, int startRowNumber, Class clazz) {
		final String[] keys = new String[clazz.getDeclaredFields().length];
		Lang.each(clazz.getDeclaredFields(), new Each<Field>() {

			@Override
			public void invoke(int index, Field field, int count) throws ExitLoop, ContinueLoop, LoopException {
				keys[index] = field.getName();
			}
		});
		return parse(Excels.getWorkbook(path).getSheet(sheetName), startRowNumber, keys);
	}

	public static <T> List<T> parseObject(String path, String sheetName, int startRowNumber, final Class<T> clazz) {
		final String[] keys = new String[clazz.getDeclaredFields().length];
		Lang.each(clazz.getDeclaredFields(), new Each<Field>() {

			@Override
			public void invoke(int index, Field field, int count) throws ExitLoop, ContinueLoop, LoopException {
				keys[index] = field.getName();
			}
		});
		List<Map> datas = parse(Excels.getWorkbook(path).getSheet(sheetName), startRowNumber, keys);
		final List<T> target = new ArrayList<T>();
		Lang.each(datas, new Each<Map>() {

			@Override
			public void invoke(int paramInt1, Map map, int paramInt2) throws ExitLoop, ContinueLoop, LoopException {
				target.add(Lang.map2Object(map, clazz));
			}
		});
		return target;
	}

	public static List<Map> parse(Sheet sheet, int startRowNumber, String[] keys) {
		List list = new ArrayList();
		for (int i = startRowNumber; i < sheet.getRows(); i++) {
			Map map = new HashMap();
			for (int j = 0; j < keys.length; j++) {
				Cell cell = sheet.getCell(j, i);
				map.put(keys[j], parse(cell));
			}
			list.add(map);
		}
		return list;
	}

	private static Object parse(Cell cell) {
		if ((cell instanceof NumberCell)) {
			String info = cell.getContents();
			if (info.indexOf(".") > 0) {
				return Double.valueOf(((NumberCell) cell).getValue());
			}
			return Long.valueOf(Long.parseLong(info));
		}
		if ((cell instanceof DateCell)) {
			return ((DateCell) cell).getDate();
		}
		if ((cell instanceof BooleanCell)) {
			return Boolean.valueOf(((BooleanCell) cell).getValue());
		}
		return cell.getContents();
	}
}