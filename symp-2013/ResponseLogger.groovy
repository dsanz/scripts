import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

String master = "@master";
String node = "@node";
String addr = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

if (addr.equals(master)) {
	Log _log = LogFactoryUtil.getLog("From " + node);
	_log.error("@log");
}

