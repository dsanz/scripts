public class GetMachineMemoryCommand extends Command {

	public GetMachineMemoryCommand(boolean isCluster) {
		super("MachineMemory", isCluster);
	}

	public void execute() {
		ProcessBuilder pb = new ProcessBuilder("free");
		pb.redirectErrorStream(true);
		Process proc = pb.start();

		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		int exit = -1;
		int lineCount=0;

		while ((line = br.readLine()) != null) {
			lineCount++
			addResult("free_" + lineCount, line);
			try {
				exit = proc.exitValue();
				if (exit == 0)  {
					// Process finished
				}
			} catch (IllegalThreadStateException t) {}
		}
	}
}
