public class MonitorMemoryUsageCommand extends ClusterCommand {

	public MonitorMemoryUsageCommand(boolean isCluster) {
		super("MemoryUsage", isCluster);
	}

	public void execute() {
		/* get monitoring info from this node */
		addResult("used", String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		addResult("total", String.valueOf(Runtime.getRuntime().totalMemory()));
		addResult("free", String.valueOf(Runtime.getRuntime().freeMemory()));
	}
}


