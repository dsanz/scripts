import com.liferay.portal.kernel.cache.CacheListenerScope
import com.liferay.portal.kernel.cache.MultiVMPoolUtil
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil
import java.lang.Thread;

public class CommandBuilder extends ScriptBuilder {
	private int _commandCount;
	private PortalCache _resultsCache;
	private CommandResultCacheListener cl;

	public CommandBuilder(String baseURL, boolean isCluster) {
		super(baseURL, isCluster)
		_commandCount = 0;
		initCommand();
		initResultCache();
	}

	private void initCommand() {
		append("core/builders/ScriptBuilder.groovy");
		append("core/resultwriter/CommandResultWriter.groovy");
		append("core/resultwriter/ClusterCommandResultWriter.groovy");
		append("core/resultwriter/LocalCommandResultWriter.groovy");
		append("core/Command.groovy");
	}

	private void initResultCache() {
		_resultsCache = MultiVMPoolUtil.getCache("COMMAND_RESULT");
		_resultsCache.removeAll();
		_resultsCache.unregisterCacheListeners();
	}

	private void initCacheListener(ResultHandler rs) {
		int clusterSize = 1;

		if (isCluster()) {
			clusterSize = ClusterExecutorUtil.getClusterNodeAddresses().size();
		}

		// create the cache listener and configure it to expect an exact number of puts
		cl = new CommandResultCacheListener(clusterSize, _commandCount);
		// register a result handler for that cache so that once all puts are there, we can notify that handler.
		cl.registerResultHandler(rs);
		// add the cache listener to the cache we want to listen
		_resultsCache.registerCacheListener(cl, CacheListenerScope.ALL);
	}

	private void removeCache() {
		_resultsCache.unregisterCacheListeners();
		_resultsCache.removeAll();
		_resultsCache.destroy();
	}

	public void appendCommand(String url, String className) {
		// create command variable name
		_commandCount++;
		String id="command_"+_commandCount;
		// add command class definition to the script
		append(url)
		// add code to create a command object of that class. Make sure we inform the command about isCluster
		appendCode("Command " + id + " = new " + className + "(" + isCluster() + ")");
		// add code to run the command
		appendCode(id + ".run()");
	}

	public void start(ResultHandler rs) {
		// dont init listener until we know the number of commands to run
		initCacheListener(rs);
		super.start();

		if (rs.isSynchronous()) {
			while (!rs.isDone()) {
				Thread.sleep(50);
			}
		}
		//removeCache();
	}
}