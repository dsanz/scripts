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
}