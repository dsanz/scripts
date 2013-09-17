import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

public abstract class ClusterCommand {

	private CommandResultWriter result;
	private String thisNode;
	private String commandName;

	public ClusterCommand(String commandName) {
		/* get local node address */
		thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
		result = new CommandResultWriter(commandName + "(" + thisNode + ")");
		this.commandName = commandName;
	}

	public abstract void execute();

	public void addResult(String key, String value) {
		result.addResult(key, value);
	}

	public void run() {
		Log _log = LogFactoryUtil.getLog(commandName + "(" + thisNode + ")");
		_log.error("Running cluster monitor command")
		execute();
		result.done();
	}
}