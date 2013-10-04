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

public class CommandResultCacheListener implements CacheListener, CommandResultListener {
	long _expectedPuts;
	Log _log;
	private boolean _done =false;
	private Map<String, String> _result;
	private List<ResultHandler> _resultHandlers;

	public CommandResultCacheListener(int clusterSize, int numberOfCommands) {
		_log = LogFactoryUtil.getLog("CommandResultCacheListener")
		setNumberOfNotifications(clusterSize * numberOfCommands);
		_resultHandlers = new ArrayList<ResultHandler>();
		_log.error("Creating CommandResultCacheListener, size: " + _expectedPuts)
		_result = new HashMap<String, String>();
	}

	public void setNumberOfNotifications(int numberOfNotifications) {
		_expectedPuts = numberOfNotifications;
	}

	public void notifyValue(String key, String value) {
		_result.put(key, value);
		_log.error("notifyEntryPut for key: " + key + ", value: " + value);
		if (_result.size() == _expectedPuts) {
			_done=true;
			for (ResultHandler rs : _resultHandlers) {
				_log.error("Notifying result handler");
				rs.done(this);
			}
		}
	}

	public JSONObject getResult() {
		JSONObject result = JSONFactoryUtil.createJSONObject();
		for (String k : keys) {
			String[] parts = StringUtil.split(k, '!');
			String node = parts[0];
			String command = parts[1];
			if (!result.has(command)) {
				result.put(command, JSONFactoryUtil.createJSONObject())
			}
			JSONObject resultPerCommand = result.getJSONObject(command);
			JSONObject resultPerCommandPerNode = JSONFactoryUtil.createJSONObject(_result.get(k));
			resultPerCommand.put(node, resultPerCommandPerNode.getJSONObject(k));
		}
		return result;
	}

	public String getResultAsString() {
		return getResult().toString().replace("\\\"", "\"");
	}

	public boolean isDone() {
		return _done;
	}

	public void registerResultHandler(ResultHandler rs) {
		_log.error("Registering result handler of class " + rs.getClass().getName())
		_resultHandlers.add(rs);
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
		notifyValue(key, value);
	}

	public void notifyEntryRemoved(
			PortalCache portalCache, Serializable key, Object value) {
		_log.debug("notifyEntryRemoved for key: " + key + ", value: " + value);
		_result.remove(key.toString());
	}

	public void notifyEntryUpdated(
			PortalCache portalCache, Serializable key, Object value) {
		_log.debug("notifyEntryUpdated for key: " + key + ", value: " + value);
	}

	public void notifyRemoveAll(PortalCache portalCache) {
		_result.clear();
	}
}
