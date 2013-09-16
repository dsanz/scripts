/* Cluster monitor prepares a set of ClusterMonitor Commands and execute them in cluster */

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.log.Log
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import com.liferay.portal.kernel.cache.CacheListener;

PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");

String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

public class ClusterMonitorCacheListener implements CacheListener {
	long _putsCount=0;
	long _expectedPuts;
	Log _log;;
	List<Serializable> keys;

	public ClusterMonitorCacheListener(int clusterSize) {
		_log = LogFactoryUtil.getLog("test")
		keys = new ArrayList<Serializable>();
		_log.error("Creating ClusterMonitorCacheListener, size: " + clusterSize)
		_expectedPuts = clusterSize;
	}

	public void notifyEntryEvicted(
			PortalCache portalCache, Serializable key, Object value) {}
	public void notifyEntryExpired(
			PortalCache portalCache, Serializable key, Object value) {}

	public void notifyEntryPut(
			PortalCache portalCache, Serializable key, Object value) {
		_putsCount++;
		_log.error("np " + _putsCount);
		keys.add(key);
		if (putsCount == _expectedPuts) {
			// process all data and create some aggregated data
			for (String k : keys) {
				_log.error("Recv data: " + k + " -> " + (Long)(portalCache.get(k)));
			}
			// output it
		}
	}
	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value) {}
	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value) {}
	public void notifyRemoveAll(PortalCache portalCache) {}
}

pc.registerCacheListener(new ClusterMonitorCacheListener(ClusterExecutorUtil.getClusterNodeAddresses().size));
sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.runCluster();


