import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

import java.lang.management.ManagementFactory ;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;

String master = "@master";
String addr = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

if (addr.equals(master)) {
	Log _log = LogFactoryUtil.getLog(addr);
	_log.error("Used Memory: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/ (1024 * 1024)) + " bytes");
	_log.error("Free Memory: " + (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " bytes");
	_log.error("Total Memory: " + (Runtime.getRuntime().totalMemory() / (1024 * 1024))+ " bytes");
	_log.error("Max Memory: " + (Runtime.getRuntime().maxMemory() / (1024 * 1024))+ " bytes");
}

