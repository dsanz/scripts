package commands

import core.Command
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

public class GetJVMInfoCommand extends Command {

	public GetJVMInfoCommand(boolean isCluster) {
		super("JVMInfo", isCluster);
	}

	public void execute() {
		RuntimeMXBean re =  ManagementFactory.getRuntimeMXBean();
		addResult("JVM name", re.getVmName())
		addResult("JVM vendor", re.getVmVendor())
		addResult("JVM version", re.getVmVersion())
		int argCount=0;
		for (String arguments : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			argCount++;
			addResult("arg"+argCount, arguments);
		}
	}
}