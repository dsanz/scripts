import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

public class ScriptBuilder {
	private String _code = "";
	private String _baseUrl;
	private boolean _isCluster;
	private int _commandId;

	public ScriptBuilder(String baseURL, boolean isCluster) {
		_baseUrl = baseURL;
		_isCluster = isCluster;
		_commandId = 0;
		appendCode("import com.liferay.portal.kernel.scripting.ScriptingUtil;");
		appendCode("ScriptingUtil.clearCache(\"groovy\");");
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

	public void appendCommand(String url, String className) {
		// add command class definition
		append(url)
		// create command variable name
		_commandId++;
		String id="command_"+_commandId;
		// add code to create an object for the command
		appendCode("Command " + id + " = new " + className + "(" + _isCluster + ")");
		// add code to run the command
		appendCode(id + ".run()");
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

	private void run() {
		ScriptingUtil.clearCache("groovy");
		ScriptingUtil.exec(null, null, "groovy", _code);
	}

	private void runCluster() {
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

	public void start() {
		if (_isCluster) {
			runCluster()
		}
		else {
			run();
		}
	}
}