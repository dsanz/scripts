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


PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");
pc.removeAll();
pc.unregisterCacheListeners();
String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
pc.registerCacheListener(new ClusterMonitorCacheListener(ClusterExecutorUtil.getClusterNodeAddresses().size()), CacheListenerScope.REMOTE);

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("CommandResultWriter.groovy");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.runCluster();
