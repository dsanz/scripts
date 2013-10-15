import com.liferay.portal.kernel.scripting.ScriptingUtil

public class Launcher extends FrameworkLoader {
	public List<String> getLogic() {
		List<String> logic = new ArrayList<String>();
		logic.add("@");
		return logic;
	}
}

code = new Launcher().getFramework();

Map<String, Object> env = new HashMap<String, Object>();
env.put("out", out);

ScriptingUtil.exec(null, env, "groovy", code);
