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
		return script.replace("\\", "\\\\").replace("\"","\\\"");
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

	public String getCode() {
		return _code;
	}

	public void run() {
		ScriptingUtil.clearCache("groovy");
		ScriptingUtil.exec(null, null, "groovy", _code);
	}

	public void runCluster() {
		try {
			Trigger t = new IntervalTrigger("execute cluster script", "request", new Date(), new Date(), 1);
			Message m = new Message();
			m.put(SchedulerEngine.LANGUAGE, "groovy");
			m.put(SchedulerEngine.SCRIPT, _code);
			SchedulerEngineUtil.schedule(t,StorageType.MEMORY, "run now", DestinationNames.SCHEDULER_SCRIPTING, m, 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}