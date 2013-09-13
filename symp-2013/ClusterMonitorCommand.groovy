import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

import java.lang.management.ManagementFactory ;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.RuntimeMXBean;

/* get local node address */
String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

/* get monitoring info from this node */
String used = "Used memory (" + thisNode + "): " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes";

/* prepare logger script with local data and run it */
sb = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
String responseLogger = sb.get("ResponseLogger.groovy");
responseLogger = responseLogger.replace("@node", thisNode);
responseLogger = responseLogger.replace("@master", master);
responseLogger = responseLogger.replace("@log", used);
sb.appendCode(responseLogger);
sb.runCluster();
