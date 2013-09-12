import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

public void execute(String script) {
    try {
        Trigger t = new IntervalTrigger("execute cluster script", "request", new Date(), new Date(), 1);
        Message m = new Message();
        m.put(SchedulerEngine.LANGUAGE, "groovy");
        m.put(SchedulerEngine.SCRIPT, script);
        SchedulerEngineUtil.schedule(t,StorageType.MEMORY, "run now", DestinationNames.SCHEDULER_SCRIPTING, m, 0);
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}
