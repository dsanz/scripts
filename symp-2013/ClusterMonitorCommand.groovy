import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

sb = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sb.appendCode(sb.replace("ResponseLogger.groovy", "@master", master));
sb.runCluster();
