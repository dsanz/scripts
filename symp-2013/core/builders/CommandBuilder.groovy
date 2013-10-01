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
		_resultsCache = MultiVMPoolUtil.getCache("CLUSTER_MONITOR");
		_resultsCache.removeAll();
		_resultsCache.unregisterCacheListeners();
	}

	private void initCacheListener(ResultHandler rs) {
		int clusterSize = 1;

		if (isCluster()) {
			clusterSize = ClusterExecutorUtil.getClusterNodeAddresses().size();
		}

		// create the cache listener and configure it to expect an exact number of puts
		cl = new CommandResutCacheListener(clusterSize, _commandCount);
		// register a result handler for that cache so that once all puts are there, we can notify that handler.
		cl.registerResultHandler(rs);
		// add the cache listener to the cache we want to listen
		_resultsCache.registerCacheListener(cl, CacheListenerScope.ALL);

	}

	public void appendCommand(String url, String className) {
		// create command variable name
		_commandCount++;
		String id="command_"+_commandCount;
		// add command class definition to the script
		append(url)
		// add code to create a command object of that class. Make sure we inform the command about isCluster
		appendCode("Command " + id + " = new " + className + "(" + _isCluster + ")");
		// add code to run the command
		appendCode(id + ".run()");
	}

	public void start(ResultHandler rs) {
		// dont init listener until we know the number of commands to run
		initCacheListener(rs);
		super.start();
	}
}