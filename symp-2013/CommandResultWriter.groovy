import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject

public class CommandResultWriter {
	private String _who;
	JSONObject result;

	public CommandResultWriter(String who) {
		_who = who;
		result = JSONFactoryUtil.createJSONObject();
		addResult("executor", _who)
	}

	public addResult(String key, String value) {
		result.put(key, value);
	}

	public String getResult() {
		return result.toString();
	}

	public void done() {
		ScriptBuilder sb = new ScriptBuilder("");
		sb.appendCode("import com.liferay.portal.kernel.cache.PortalCache");
		sb.appendCode("import com.liferay.portal.kernel.cache.MultiVMPoolUtil");
		sb.appendCode("PortalCache pc = MultiVMPoolUtil.getCache(\"CLUSTER_MONITOR\");")
		sb.appendCode("pc.put(\"" + _who + "\", \"" + getResult().replace("\"", "\\\"") + "\");");
		sb.runCluster();
	}
}