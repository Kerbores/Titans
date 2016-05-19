package club.zhcs.jxls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import club.zhcs.jxls.anno.Title;
import club.zhcs.jxls.entity.Row;

public class Titles {
	public static Row createSubTitle(int rNum, List infos) {
		return new Row(rNum, infos);
	}

	public static Row createSubTitle(int rNum, Object obj) {
		Class clazz = null;
		List list = new ArrayList();
		if ((obj instanceof Class))
			clazz = (Class) obj;
		else {
			clazz = obj.getClass();
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Title title = field.getAnnotation(Title.class);
			if (title != null) {
				list.add(title.value());
			}

		}

		return new Row(rNum, list);
	}
}