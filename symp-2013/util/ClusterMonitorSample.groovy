/* Cluster monitor prepares a set of Commands and executes them in cluster */

out.print("Starting...");

boolean isCluster=true;

cb = new CommandBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/commands/", isCluster);
cb.appendCommand("MemoryUsageCommand.groovy", "MemoryUsage");
cb.appendCommand("GetPortalImplManifestAttrsCommand.groovy", "ManifestAttrs");
cb.start(new LiferayConsoleOutputHandler(out));


