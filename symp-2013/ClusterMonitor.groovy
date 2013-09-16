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

out.print("Starting...");

ClusterMonitorCacheListener cl = new ClusterMonitorCacheListener(ClusterExecutorUtil.getClusterNodeAddresses().size());
pc.registerCacheListener(cl, CacheListenerScope.ALL);

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("CommandResultWriter.groovy");
sbCommand.append("ClusterCommand.groovy");
sbCommand.append("ClusterMonitorMemoryUsageCommand.groovy");
sbCommand.runCluster();

while (!cl.isDone()) {
	Thread.sleep(50);
}
out.print("done!<br>");
out.print("<div id='clustermonitorresult'></div>");
out.println("<script src='https://raw.github.com/padolsey/prettyPrint.js/master/prettyprint.js'></script>");
out.println("<script type='text/javascript'>");
out.println("var r=" + cl.getResultAsString() + ";");
out.println("var tbl = prettyPrint(r);");
out.println("document.getElementById('clustermonitorresult').appendChild(tbl);");
out.println("</script>");

