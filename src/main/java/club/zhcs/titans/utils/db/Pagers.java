package club.zhcs.titans.utils.db;

import java.util.ArrayList;
import java.util.List;

import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.util.NutMap;

/**
 * @author 王贵源
 *
 *         create at 2015年4月28日 下午4:36:16
 */
public class Pagers<T> {

	private Class<T> clazz;

	/**
	 * @param clazz
	 */
	public Pagers(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}

	public Pager<T> getFromResult(Result result) {
		NutMap data = result.getData();
		NutMap pager = data.getAs("pager", NutMap.class);
		Pager<T> pager_ = new Pager<T>();
		pager_.setCount(pager.getInt("count"));
		pager_.setPage(pager.getInt("page"));
		pager_.setPageSize(pager.getInt("pageSize"));

		List datas = pager.getAs("entities", List.class);
		List<T> data_ = new ArrayList<T>();
		for (Object d : datas) {
			data_.add(Lang.map2Object(Lang.map(Json.toJson(d)), clazz));
		}
		pager_.setEntities(data_);
		return pager_;
	}
}
