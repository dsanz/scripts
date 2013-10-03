public class GetCPUInfoCommand extends Command {

	public GetCPUInfoCommand(boolean isCluster) {
		super("CPUInfo", isCluster);
	}

	public void execute() {
		ProcessBuilder pb = new ProcessBuilder("cat", "/proc/cpuinfo");
		pb.redirectErrorStream(true);
		Process proc = pb.start();

		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		int exit = -1;
		int cpuCount=0;

		while ((line = br.readLine()) != null) {
			String param = line.substring(0, line.indexOf(":") - 1).trim();
			if ("processor".equals(param)) {
				cpuCount++;
			}
			String value = line.substring(line.indexOf(":"));
			addResult(param + "_" + cpuCount, value);
			try {
				exit = proc.exitValue();
				if (exit == 0)  {
					// Process finished
				}
			} catch (IllegalThreadStateException t) {}
		}
	}
}
