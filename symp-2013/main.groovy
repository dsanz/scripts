import com.liferay.portal.kernel.scripting.ScriptingUtil;
String baseURL = "https://raw.github.com/dsanz/scripts/master/symp-2013/"

String frameworkLoader = new URL(baseURL + "util/FrameworkLoader.groovy").text
String clusterMonitor = new URL(baseURL + "util/ClusterMonitorSample.groovy").text

Map<String, Object> env = new HashMap<String, Object>();
env.put("out", out);

ScriptingUtil.exec(null, env, "groovy", frameworkLoader + "\n" +  clusterMonitor);