import com.liferay.portal.kernel.cache.CacheListenerScope
import com.liferay.portal.kernel.cache.MultiVMPoolUtil
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

public class CommandBuilder extends ScriptBuilder {
	private int _commandCount;
	private PortalCache _resultsCache;
	private CommandResutCacheListener cl;

	public CommandBuilder(String baseURL, boolean isCluster) {
		super(baseURL, isCluster)
		_commandCount = 0;
		initCommand();
		initResultCache();
	}

	private void initCommand() {
		append("ScriptBuilder.groovy");
		append("CommandResultWriter.groovy");
		append("ClusterCommandResultWriter.groovy");
		append("LocalCommandResultWriter.groovy");
		append("Command.groovy");
	}

	private void initResultCache() {
		PortalCache pc = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");
		pc.removeAll();
		pc.unregisterCacheListeners();
	}

	private void initCacheListener() {
		int clusterSize = 1;

		if (isCluster()) {
			clusterSize = ClusterExecutorUtil.getClusterNodeAddresses().size();
		}

		cl = new CommandResutCacheListener(clusterSize, _commandCount);
		_resultsCache.registerCacheListener(cl, CacheListenerScope.ALL);
	}

	public void appendCommand(String url, String className) {
		// add command class definition
		append(url)
		// create command variable name
		_commandCount++;
		String id="command_"+_commandCount;
		// add code to create an object for the command
		appendCode("Command " + id + " = new " + className + "(" + _isCluster + ")");
		// add code to run the command
		appendCode(id + ".run()");
	}

	public void start() {
		// dont init listener until we know the number of commands to run
		initCacheListener();
		if (_isCluster) {
			runCluster()
		}
		else {
			run();
		}
	}
}