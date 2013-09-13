/* Cluster monitor prepares a set of ClusterMonitor Commands and execute them in cluster */

sbCommand = new ScriptBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/");
sbCommand.append("ScriptBuilder.groovy");
sbCommand.append("ClusterMonitorCommand.groovy");
sbCommand.echo();
sbCommand.runCluster();


