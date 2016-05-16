package club.zhcs.titans.utils.test.runner;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 多线程并发测试类
 * 
 * @author Kerbores
 * 
 */
public class MutilThreadRunner {
	public static void run(int count, TestTask task) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		task.setCyclicBarrier(cyclicBarrier);
		for (int i = 0; i < count; i++)
			executorService.execute(task);
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}
	}

	private static final Log log = Logs.get();
}
