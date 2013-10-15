package core.resultwriter;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;

public class LocalCommandResultWriter extends CommandResultWriter {
	public LocalCommandResultWriter(String command) {
		super("localhost!" + command);
	}

	public void done() {
		PortalCache pc = MultiVMPoolUtil.getCache("COMMAND_RESULT");
		pc.put(getCacheKey(), getResult());
	}
}