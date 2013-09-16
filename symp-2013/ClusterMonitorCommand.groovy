import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;


/* get local node address */
String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

CommandResultWriter result = new CommandResultWriter(thisNode);

/* get monitoring info from this node */
Long used = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

Log _log = LogFactoryUtil.getLog(thisNode);
_log.error("Running cluster monitor command")

result.addResult("used", String.valueOf(used));

result.done();

