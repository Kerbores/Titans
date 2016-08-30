package club.zhcs.jxls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.nutz.lang.Strings;

import club.zhcs.jxls.anno.Ignore;
import club.zhcs.jxls.anno.Title;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project jxls
 *
 * @file FieldMapper.java
 *
 * @description 属性映射器
 *
 * @time 2016年5月26日 下午3:12:38
 *
 */
public class FieldMapper {

	public static class MapperNode {
		/**
		 * 
		 */
		private String field;

		/**
		 * 标题
		 */
		private String header;

		/**
		 * @param header
		 * @param field
		 */
		public MapperNode(String header, String field) {
			super();
			this.header = header;
			this.field = field;
		}

		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}

		/**
		 * @return the header
		 */
		public String getHeader() {
			return header;
		}

		/**
		 * @param field
		 *            the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}

		/**
		 * @param header
		 *            the header to set
		 */
		public void setHeader(String header) {
			this.header = header;
		}

	}

	private List<MapperNode> nodes;

	public FieldMapper(Object obj) {
		super();
		List<MapperNode> nodes = new ArrayList<FieldMapper.MapperNode>();
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (!field.isAccessible())
				field.setAccessible(true);
			try {
				if ((field.getAnnotation(Ignore.class) != null) && (field.getAnnotation(Ignore.class).value()))
					continue;
				Title title = field.getAnnotation(Title.class);
				nodes.add(new MapperNode(title == null || Strings.isBlank(title.value()) ? field.getName() : title.value(), field.getName()));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		this.nodes = nodes;
	}

	/**
	 * @param nodes
	 */
	public FieldMapper(List<MapperNode> nodes) {
		super();
		this.nodes = nodes;
	}

	/**
	 * @return the nodes
	 */
	public List<MapperNode> getNodes() {
		return nodes;
	}

	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(List<MapperNode> nodes) {
		this.nodes = nodes;
	}

}
