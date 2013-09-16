import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

public abstract class ClusterCommand {

	private CommandResultWriter result;

	public ClusterCommand() {
		/* get local node address */
		String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
		result = new CommandResultWriter(thisNode);
	}

	public abstract void execute();

	public void addResult(String key, String value) {
		result.addResult(key, value);
	}

	public void run() {
		Log _log = LogFactoryUtil.getLog(thisNode);
		_log.error("Running cluster monitor command")
		execute();
		result.done();
	}
}