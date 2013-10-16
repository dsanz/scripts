package util;

import com.liferay.portal.kernel.util.StringBundler;
import java.util.List;
import java.util.ArrayList
import java.util.regex.Pattern;


public abstract class FrameworkLoader {
	private String[] packages = ["commands","core","util"]

	public String unpack(String code) {
		for (String pkg : packages) {
			code = code.replaceAll("package " + pkg + ".*", "");
			code = code.replaceAll("import " + pkg + ".*", "");
		}
		return code;
	}

	public String toCode(ArrayList<String>... urlsList) {
		String baseURL = "https://raw.github.com/dsanz/scripts/master/symp-2013/"
		StringBundler code = new StringBundler();
		for (ArrayList<String> urlList : urlsList) {
			for (String url : urlList) {
				code.append(unpack(new URL(baseURL + url).text));
				code.append("\n");
			}
		}
		return code.toString();
	}

	private List<String> getInterfaces() {
		List<String> interfaces = new ArrayList<String>();
		interfaces.add("core/resulthandlers/ResultHandler.groovy");
		interfaces.add("core/resultwriter/CommandResultWriter.groovy");
		interfaces.add("core/resultlistener/CommandResultListener.groovy");
		interfaces.add("core/Command.groovy");
		return interfaces;
	}

	public List<String> getClasses() {
		List<String> classes = new ArrayList<String>();
		classes.add("core/builders/ScriptBuilder.groovy");
		classes.add("core/builders/CommandBuilder.groovy");
		classes.add("core/resulthandlers/LiferayConsoleOutputHandler.groovy");
		classes.add("core/resultlistener/CommandResultCacheListener.groovy");
		classes.add("core/resultwriter/ClusterCommandResultWriter.groovy");
		classes.add("core/resultwriter/LocalCommandResultWriter.groovy");
		return classes;
	}

	public abstract List<String> getLogic();

	public String getFramework() {
		return toCode(getInterfaces(), getClasses(), getLogic())
	}
}
