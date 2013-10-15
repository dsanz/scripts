package core.resultwriter;

import com.liferay.portal.kernel.json.JSONFactoryUtil
import com.liferay.portal.kernel.json.JSONObject

public abstract class CommandResultWriter {
	private String _cacheKey;
	private JSONObject _result;
	private JSONObject _payload;

	public CommandResultWriter(String cacheKey) {
		_result = JSONFactoryUtil.createJSONObject();
		_payload = JSONFactoryUtil.createJSONObject();
		_cacheKey = cacheKey;
		_result.put(_cacheKey, _payload);
	}

	public String getCacheKey() {
		return _cacheKey;
	}

	public addResult(String key, String value) {
		_payload.put(key, value);
	}

	public String getResult() {
		return _result.toString();
	}

	public abstract void done();
}