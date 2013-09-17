import com.liferay.portal.kernel.cluster.ClusterExecutorUtil
import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject

public class CommandResultWriter {
	private String _cacheKey;
	private JSONObject _result;
	private JSONObject _payload;

	public CommandResultWriter(String command) {
		String thisNode = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
		_cacheKey = thisNode + "!" + command;
		System.out.println("Cacke KEY: " + _cacheKey);
		_result = JSONFactoryUtil.createJSONObject();
		_payload = JSONFactoryUtil.createJSONObject();
		_result.put(command, _payload);
	}

	public addResult(String key, String value) {
		_payload.put(key, value);
	}

	public String getResult() {
		return _result.toString();
	}

	public void done() {
		ScriptBuilder sb = new ScriptBuilder("")
		sb.appendCode("import com.liferay.portal.kernel.log.LogFactoryUtil;");
		sb.appendCode("import com.liferay.portal.kernel.cache.PortalCache");
		sb.appendCode("import com.liferay.portal.kernel.cache.MultiVMPoolUtil")
		sb.appendCode("_log = LogFactoryUtil.getLog(\"ResultWriter_" + _cacheKey + "\");");
		sb.appendCode("_log.error(\"Running command result writer\");");
		sb.appendCode("PortalCache pc = MultiVMPoolUtil.getCache(\"CLUSTER_MONITOR\");")
		sb.appendCode("pc.put(\"" + _cacheKey + "\", \"" + getResult().replace("\"", "\\\"") + "\");");
		sb.runCluster();
	}
}