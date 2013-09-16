/* Cluster monitor prepares a set of ClusterMonitor Commands and execute them in cluster */

import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.log.Log
import com.liferay.portal.kernel.log.LogFactoryUtil;

PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");



String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.runCluster();

pc.registerCacheListener(new CacheListener(){
	long _putsCount=0;
	long _expectedPuts=ClusterExecutorUtil.getClusterNodeAddresses().size();
	Log _log = LogFactoryUtil.getLog("test");

	List<Serializable> keys = new ArrayList<Serializable>();

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
				PortalCache pcache = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");
				for (String k : keys) {
					_log.error("Recv data: " + k + " -> " + (Long)(pcache.get(k)));
				}
				// output it
			}
		}
		public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value) {}
		public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value) {}
		public void notifyRemoveAll(PortalCache portalCache) {}

})


