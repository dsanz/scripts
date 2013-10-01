import com.liferay.portal.kernel.scripting.ScriptingUtil;
import java.util.Map;
import java.util.HashMap;

String baseURL = "https://raw.github.com/dsanz/scripts/cache/symp-2013/"

String scriptBuilder = new URL(baseURL + "core/builders/ScriptBuilder.groovy").text
String clusterMonitor = new URL(baseURL + "util/ClusterMonitor.groovy").text

Map<String, Object> env = new HashMap<String, Object>();
env.put("out", out);

ScriptingUtil.exec(null, env, "groovy", scriptBuilder + "\n" +  clusterMonitor);