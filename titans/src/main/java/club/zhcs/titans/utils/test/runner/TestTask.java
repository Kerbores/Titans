package club.zhcs.titans.utils.test.runner;

import java.util.concurrent.CyclicBarrier;

import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 测试任务
 * 
 * @author Kerbores
 * 
 */
public abstract class TestTask implements Runnable {
	private static final Log log = Logs.get();
	private CyclicBarrier cyclicBarrier;

	public TestTask() {
	}

	public CyclicBarrier getCyclicBarrier() {
		return cyclicBarrier;
	}

	@Override
	public void run() {
		try {
			runTask();
			cyclicBarrier.await();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected abstract void runTask();

	public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
		this.cyclicBarrier = cyclicBarrier;
	}

}
