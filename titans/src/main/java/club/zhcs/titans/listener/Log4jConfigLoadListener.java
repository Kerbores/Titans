package club.zhcs.titans.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.resource.Scans;

/**
 * @author kerbores
 *
 * @email kerbores@gmail.com
 * 
 *        log4j 的配置环境感知监听器
 */
public class Log4jConfigLoadListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Scans.me().init(event.getServletContext());// 非 nutzmvc 环境加载 nutz 的资源扫描器
		String log4jConfigPath = event.getServletContext().getInitParameter("log4j.config.path");
		try {
			if (Strings.isNotBlank(log4jConfigPath) && Files.checkFile(log4jConfigPath) != null) {// 有配置
				PropertyConfigurator.configure(log4jConfigPath);
			}
		} catch (Exception e) {// do nothing
		}
	}

}
