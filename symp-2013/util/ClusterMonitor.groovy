/* Cluster monitor prepares a set of Commands and executes them in cluster */

out.print("Starting...");

boolean isCluster=true;

sbCommand = new CommandBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/", isCluster);
sbCommand.appendCommand("commands/MemoryUsageCommand.groovy", "MemoryUsageCommand");
sbCommand.appendCommand("commands/GetPortalImplManifestAttrsCommand.groovy", "GetPortalImplManifestAttrsCommand");
sbCommand.start(new LiferayConsoleOutputHandler(out));


