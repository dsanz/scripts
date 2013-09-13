/* Cluster monitor prepares a set of ClusterMonitor Commands and execute them in cluster */

String master = ClusterExecutorUtil.getLocalClusterNodeAddress().getRealAddress();

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.appendCode("master=\""+  master + "\"");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.runCluster();


