import com.liferay.portal.kernel.cluster.ClusterExecutorUtil
import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject

public class ClusterCommandResultWriter extends CommandResultWriter {
	public ClusterCommandResultWriter(String command) {
		String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
		super(thisNode + "!" + command);
	}

	public void done() {
		ScriptBuilder sb = new ScriptBuilder(true);
		sb.appendCode("import com.liferay.portal.kernel.log.LogFactoryUtil;");
		sb.appendCode("import com.liferay.portal.kernel.cache.PortalCache");
		sb.appendCode("import com.liferay.portal.kernel.cache.MultiVMPoolUtil")
		sb.appendCode("_log = LogFactoryUtil.getLog(\"ClusterResultWriter_" + _cacheKey + "\");");
		sb.appendCode("_log.error(\"Running command result writer\");");
		sb.appendCode("PortalCache pc = MultiVMPoolUtil.getCache(\"COMMAND_RESULT\");")
		sb.appendCode("pc.put(\"" + getCacheKey() + "\", \"" + getResult().replace("\"", "\\\"") + "\");");
		sb.start();
	}
}