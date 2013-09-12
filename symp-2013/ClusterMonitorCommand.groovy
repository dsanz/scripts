import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;

sb = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sb.append("ClusterScriptRunner.groovy")
String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();
String responseScript = sb.replace("ResponseLogger.groovy", "@master", master);
sb.replace("@script", sb.escape(responseScript));
sb.echo();
sb.run();