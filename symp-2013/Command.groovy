import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

public abstract class Command {
	private CommandResultWriter result;
	private String commandName;

	public Command(String commandName) {
		this.commandName = commandName;
		setResultWriter(buildResultWriter());
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

	public abstract CommandResultWriter buildResultWriter();

	public void run() {
		execute();
		result.done();
	}
}