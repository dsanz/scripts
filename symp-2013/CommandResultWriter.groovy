import com.liferay.portal.kernel.cluster.ClusterExecutorUtil
import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject

public class CommandResultWriter {
	private String _who;
	JSONObject result;
	JSONObject payload;

	public CommandResultWriter(String key) {
		_who = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress() + "@" + key;
		result = JSONFactoryUtil.createJSONObject();
		payload = JSONFactoryUtil.createJSONObject();
		result.put(key, payload);
	}

	public addResult(String key, String value) {
		payload.put(key, value);
	}

	public String getResult() {
		return result.toString();
	}

	public void done() {
		ScriptBuilder sb = new ScriptBuilder("")
		sb.appendCode("import com.liferay.portal.kernel.log.LogFactoryUtil;");
		sb.appendCode("import com.liferay.portal.kernel.cache.PortalCache");
		sb.appendCode("import com.liferay.portal.kernel.cache.MultiVMPoolUtil")
		sb.appendCode("_log = LogFactoryUtil.getLog(\"CommandResultWriter_" + _who + "\");");
		sb.appendCode("_log.error(\"Running command result writer\");");
		sb.appendCode("PortalCache pc = MultiVMPoolUtil.getCache(\"CLUSTER_MONITOR\");")
		sb.appendCode("pc.put(\"" + _who + "\", \"" + getResult().replace("\"", "\\\"") + "\");");
		sb.runCluster();
	}
}