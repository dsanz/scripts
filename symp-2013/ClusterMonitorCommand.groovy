import com.liferay.portal.kernel.cache.MultiVMPoolUtil
import com.liferay.portal.kernel.cache.PortalCache

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

/* get local node address */
String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

/* get monitoring info from this node */
Long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

Log _log = LogFactoryUtil.getLog("local");
_log.error("Running cluster monitor command")

PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");

pc.put(thisNode, used);

