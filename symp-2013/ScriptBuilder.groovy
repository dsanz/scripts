import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

public class ScriptBuilder {
	private String _code = "";
	private String _baseUrl;

	public ScriptBuilder(String baseURL) {
		_baseUrl = baseURL;
	}

	public void addVariable(String name, String value) {
		appendCode(name + " = \"" + value + "\"");
	}

	public void append(String url) {
		appendCode(get(url));
	}

	public void appendCode(String script) {
		_code = _code + (_code.length() == 0 ? "" : "\n") + script;
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