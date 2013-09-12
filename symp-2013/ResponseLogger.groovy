import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

String master = "@master";

public class ResponseLogger {
	int a;
	int b;

	public ResponseLogger(int a, int b) {
		this.a = a;
		this.b=b;
	}

	public void log(String addr) {
		Log _log = LogFactoryUtil.getLog(addr);
		_log.error("hi from " + addr + ". My values are a=" + a + ", b=" + b);
	}
}

String addr = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
if (addr.equals(master)) {
	new ResponseLogger(10,17).log(addr);
}

