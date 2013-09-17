import com.liferay.portal.kernel.cache.PortalCache
import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cache.CacheListener;
import com.liferay.portal.kernel.util.StringUtil;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class ClusterMonitorCacheListener implements CacheListener {
	long _expectedPuts;
	Log _log;
	Set<String> keys;
	private boolean done=false;
	private Map<String, String> _result;

	public ClusterMonitorCacheListener(int clusterSize, int numberOfCommands) {
		_log = LogFactoryUtil.getLog("ClusterMonitorCacheListener")
		keys = new HashSet<String>();
		_expectedPuts = clusterSize*numberOfCommands;
		_log.error("Creating ClusterMonitorCacheListener, size: " + _expectedPuts)
		_result = new HashMap<String, String>();
	}

	public void notifyEntryEvicted(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryEvicted");
	}
	public void notifyEntryExpired(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryExpired");
	}

	public void notifyEntryPut(
			PortalCache portalCache, Serializable key, Object value) {
		keys.add(key.toString());
		_log.error("notifyEntryPut for key: " + key + ", value: " + value);
		if (keys.size() == _expectedPuts) {
			// process all data and create some aggregated data
			for (String k : keys) {
				_result.put(k, portalCache.get(k));
				_log.error("Recv data: " + k + " -> " + portalCache.get(k));
			}
			done=true;
		}
	}

	public JSONObject getResult() {
		JSONObject result = JSONFactoryUtil.createJSONObject();
		for (String k : keys) {
			String[] parts = StringUtil.split(k, "@");
			String node = parts[0];
			String command = parts[1];
			if (!result.has(command)) {
				result.put(command,JSONFactoryUtil.createJSONObject())
			}
			JSONObject resultPerNode = result.getJSONObject(command);

			resultPerNode.put(node, JSONFactoryUtil.createJSONObject(_result.get(k)))
		}
		return result;
	}

	public String getResultAsString() {
		return getResult().toString().replace("\\\"", "\"");
	}

	public boolean isDone() {
		return done;
	}

	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryRemoved for key: " + key + ", value: " + value);
		keys.remove(key.toString());
	}
	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value) {
		_log.error("notifyEntryUpdated for key: " + key + ", value: " + value);
	}
	public void notifyRemoveAll(PortalCache portalCache) {}
}
