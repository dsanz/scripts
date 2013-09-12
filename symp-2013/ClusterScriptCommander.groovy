import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.scheduler.CronTrigger;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

String master=ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
String lang="groovy";
String jobname="send script to cluster";
String groupname="request"

public void execute(String script) {
    try {
        Trigger t new IntervalTrigger(jobname, groupname, new Date(), new Date(), 1);
        Message m = new Message();
        m.put(SchedulerEngine.LANGUAGE, lang);
        m.put(SchedulerEngine.SCRIPT, script);
        SchedulerEngineUtil.schedule(t,StorageType.MEMORY, groupname+jobname, DestinationNames.SCHEDULER_SCRIPTING, m, 0);
    }
    catch (Exception e) {e.printStackTrace();}
}

String script=new URL("https://raw.github.com/dsanz/scripts/master/symp-2013/ResponseLogger.groovy").text

execute(script);







