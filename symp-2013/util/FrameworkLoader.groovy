import com.liferay.portal.kernel.util.StringBundler;

public class FrameworkLoader {
	public String toCode(ArrayList<String>... urlsList) {
		String baseURL = "https://raw.github.com/dsanz/scripts/master/symp-2013/"
		String code = new StringBundler();
		for (ArrayList<String> urlList : urlsList) {
			for (String url : urlList) {
				code.append(new URL(baseURL + url).text);
				code.append("\n");
			}
		}
		return code.toString();
	}

	private List<String> getInterfaces() {
		interfaces = new ArrayList<String>();
		interfaces.add("core/resulthandlers/ResultHandler.groovy");
		interfaces.add("core/resultwriters/CommandResultWriter.groovy");
		interfaces.add("core/resultlistener/CommandResultListener.groovy");
		interfaces.add("core/Command.groovy");
		return interfaces;
	}

	public List<String> getClasses() {
		classes = new ArrayList<String>();
		classes.add("core/builders/ScriptBuilder.groovy");
		classes.add("core/builders/CommandBuilder.groovy");
		classes.add("core/resulthandlers/LiferayConsoleOutputHandler.groovy");
		classes.add("core/resultlistener/CommandResultCacheListener.groovy");
		classes.add("core/resultwriter/ClusterCommandResultWriter.groovy");
		classes.add("core/resultwriter/LocalCommandResultWriter.groovy");
		return classes;
	}

	public List<String> getUtils() {
		utils = new ArrayList<String>();
		utils.add("util/ClusterMonitorSample.groovy")
		return utils;
	}

	public String getFramework() {
		return toCode(getInterfaces(), getClasses(), getUtils())
	}

}

framework = new Framework().getFramework()
