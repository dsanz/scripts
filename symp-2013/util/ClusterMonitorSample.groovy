/* Cluster monitor prepares a set of Commands and executes them in cluster */

out.print("Starting...");

boolean isCluster=true;

cb = new CommandBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/", isCluster);
cb.appendCommand("commands/MemoryUsageCommand.groovy", "MemoryUsage");
cb.appendCommand("commands/GetPortalImplManifestAttrsCommand.groovy", "ManifestAttrs");
cb.start(new LiferayConsoleOutputHandler(out));


