package club.zhcs.titans.nutz.excel;

import club.zhcs.titans.utils.db.po.Entity;

/**
 * @author JiangKun
 * @date 2016年11月23日 下午3:17:22
 */
public class NutzExcelExporterFactory {

	public static <T extends Entity> EntityExcelExporter<T> getEntityExporter(final Class<T> requiredType) {
		return new NutzEntityExcelExporter();
	}

	public static RecordExcelExporter getRecordExporter() {
		return new NutzRecordExcelExporter();
	}

}
