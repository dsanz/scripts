import com.liferay.portal.kernel.scripting.ScriptingUtil;

String baseURL = "https://raw.github.com/dsanz/scripts/master/symp-2013/"

String logic = new URL(baseURL + "@").text

Map<String, Object> env = new HashMap<String, Object>();
env.put("out", out);

ScriptingUtil.exec(null, env, "groovy", framework + "\n" + logic);