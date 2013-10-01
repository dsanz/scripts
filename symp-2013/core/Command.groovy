public abstract class Command {
	private CommandResultWriter result;
	private String commandName;

	public Command(String commandName, boolean isCluster) {
		this.commandName = commandName;
		if (isCluster) {
			setResultWriter(new ClusterCommandResultWriter(commandName));
		}
		else {
			setResultWriter(new LocalCommandResultWriter(commandName));
		}
	}

	public abstract void execute();

	public void addResult(String key, String value) {
		result.addResult(key, value);
	}

	public String getName() {
		return commandName;
	}

	public void setResultWriter(CommandResultWriter resultWriter) {
		result = resultWriter;
	}

	public void run() {
		execute();
		result.done();
	}
}