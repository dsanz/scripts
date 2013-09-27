import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

public abstract class LocalCommand extends Command {

	public LocalCommand(String commandName) {
		super(commandName);
	}

	public CommandResultWriter buildResultWriter() {
		return new LocalCommandResultWriter(getCommandName());
	}

	public void run() {
		Log _log = LogFactoryUtil.getLog(commandName + "(localhost)");
		_log.error("Running local command " + commandName)
		super.run();
	}
}
