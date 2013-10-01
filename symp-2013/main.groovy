import com.liferay.portal.kernel.scripting.ScriptingUtil;
String baseURL = "https://raw.github.com/dsanz/scripts/master/symp-2013/"

String launcher = new URL(baseURL + "util/launcher.groovy").text

launcher.replace("@", "util/ClusterMonitorSample.groovy")

Map<String, Object> env = new HashMap<String, Object>();
env.put("out", out);

ScriptingUtil.exec(null, env, "groovy", launcher);