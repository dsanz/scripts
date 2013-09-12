import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

public class ScriptBuilder {
	private String _code = "";
	private String _baseUrl;

	public ScriptBuilder(String baseURL) {
		_baseUrl = baseURL;
	}

	public void append(String url) {
		_code = _code + "\n" + get(url);
	}

	public String get(String url) {
		return new URL(_baseUrl + url).text
	}

	public String escape(String script) {
		return script.replace("\"","\\\"").replace("\n","\\n");
	}

	public void replace(String text, String replacement) {
		_code = _code.replace(text, replacement);
	}

	public String replace(String url, String text, String replacement) {
		return get(url).replace(text, replacement);
	}

	public void echo() {
		System.out.print(_code);
	}

	public void run() {
		ScriptingUtil.clearCache("groovy");
		ScriptingUtil.exec(null, null, "groovy", _code);
	}
}