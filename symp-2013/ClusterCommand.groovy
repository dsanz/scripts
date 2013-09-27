import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

public abstract class ClusterCommand {
	String thisNode;

	public ClusterCommand(String commandName) {
		/* get local node address */
		thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
		super(commandName);
	}

	public CommandResultWriter buildResultWriter() {
		return new ClusterCommandResultWriter(getCommandName());
	}

	public void run() {
		Log _log = LogFactoryUtil.getLog(commandName + "(" + thisNode + ")");
		_log.error("Running cluster command " + commandName)
		super.run();
	}
}