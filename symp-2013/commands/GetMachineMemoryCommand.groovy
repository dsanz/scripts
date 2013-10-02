import com.liferay.portal.kernel.util.StringBundler;

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
		StringBundler sb = new StringBundler();

		while ((line = br.readLine()) != null) {
			sb.append(line);
			try {
				exit = proc.exitValue();
				if (exit == 0)  {
					// Process finished
				}
			} catch (IllegalThreadStateException t) {}
		}
		/* get monitoring info from this node */
		addResult("free", sb.toString());
	}
}
