/* Cluster monitor prepares a set of ClusterMonitor Commands and execute them in cluster */

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.log.Log
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;


import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.cache.CacheListenerScope;

public class ClusterMonitorCacheListener implements CacheListener {
	long _expectedPuts;
	Log _log;
	Set<String> keys;

	public ClusterMonitorCacheListener(int clusterSize) {
		_log = LogFactoryUtil.getLog("ClusterMonitorCacheListener")
		keys = new HashSet<String>();
		_log.error("Creating ClusterMonitorCacheListener, size: " + clusterSize)
		_expectedPuts = clusterSize;
	}

	public void notifyEntryEvicted(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryEvicted");
	}
	public void notifyEntryExpired(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryExpired");
	}

	public void notifyEntryPut(
			PortalCache portalCache, Serializable key, Object value) {
		keys.add(key.toString());
		_log.error("notifyEntryPut for key: " + key);
		if (keys.size() == _expectedPuts) {
			// process all data and create some aggregated data
			for (String k : keys) {
				_log.error("Recv data: " + k + " -> " + (Long)(portalCache.get(k)));
			}
			// output it
		}
	}
	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryRemoved for key: " + key);
		keys.remove(key.toString());
	}
	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryUpdated for key: " + key);
	}
	public void notifyRemoveAll(PortalCache portalCache) {}
}

PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");
pc.removeAll();
pc.unregisterCacheListeners();
String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
pc.registerCacheListener(new ClusterMonitorCacheListener(ClusterExecutorUtil.getClusterNodeAddresses().size()), CacheListenerScope.REMOTE);

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.runCluster();


