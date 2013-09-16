import com.liferay.portal.kernel.cache.MultiVMPoolUtil
import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;


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
		sb.appendCode("PortalCache pc = MultiVMPoolUtil.getCache(\"CLUSTER_MONITOR\");")
		sb.appendCode("pc.put(" + _who + ", " + getResult() + ");");
		sb.runCluster();
	}
}