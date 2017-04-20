package club.zhcs.titans.gather.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.Sigar;
import org.nutz.json.Json;
import org.nutz.lang.Strings;

import club.zhcs.titans.gather.CPUGather;
import club.zhcs.titans.gather.DISKGather;
import club.zhcs.titans.gather.Gathers;
import club.zhcs.titans.gather.MemoryGather;
import club.zhcs.titans.gather.NetInterfaceGather;
import club.zhcs.titans.gather.OSGather;
import club.zhcs.titans.utils.db.Result;

public class SigarServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum APIType {
		/**
		 * cpu信息
		 */
		CPU,
		/**
		 * 内存信息
		 */
		MEM,
		/**
		 * 磁盘信息
		 */
		DISK,
		/**
		 * 网卡信息
		 */
		NI,
		/**
		 * 系统信息
		 */
		SYS,
		/**
		 * 全部
		 */
		ALL,
		/**
		 * 默认
		 */
		DEFAULT;
		public static APIType form(String t) {
			for (APIType type : values()) {
				if (Strings.equalsIgnoreCase(type.name(), t)) {
					return type;
				}
			}
			return DEFAULT;
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String _type = req.getParameter("type");
			APIType type = APIType.form(_type);
			Sigar sigar = new Sigar();
			Result result = Result.success();
			type = null == type ? APIType.DEFAULT : type;
			switch (type) {
			case CPU:
				result.addData("cpugather", CPUGather.gather((sigar)));
				break;
			case DISK:
				result.addData("disks", DISKGather.gather(sigar));
				break;
			case NI:
				result.addData("adapters", NetInterfaceGather.gather(sigar));
				break;
			case SYS:
				result.addData("os", OSGather.init(sigar));
				break;
			case MEM:
				result.addData("memory", MemoryGather.gather(sigar));
				break;
			case ALL:
				result.addData(Gathers.all());
				break;
			default:
				result.addData("apis", APIType.values()).addData("discription", "use type parameter to invoke apis like type=SYS");
			}
			resp.setContentType("application/json");
			resp.getWriter().write(Json.toJson(result));
		} catch (Exception e) {
		}
	}
}
