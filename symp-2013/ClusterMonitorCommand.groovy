import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

/* new code */
sb = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
sb.appendCode(sb.replace("ResponseLogger.groovy", "@master", master));
sb.echo();
sb.runCluster();

/* old code */
/*
sb = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sb.append("ClusterScriptRunner.groovy")
String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
String responseScript = sb.replace("ResponseLogger.groovy", "@master", master);
sb.replace("@script", sb.escape(responseScript));
sb.echo();
sb.run();*/