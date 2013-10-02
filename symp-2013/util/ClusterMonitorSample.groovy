/* Cluster monitor prepares a set of Commands and executes them in cluster */

out.print("Starting...");

boolean isCluster=true;

cb = new CommandBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/", isCluster);
cb.appendCommand("commands/MemoryUsageCommand.groovy", "MemoryUsageCommand");
cb.appendCommand("commands/GetPortalImplManifestAttrsCommand.groovy", "GetPortalImplManifestAttrsCommand");
cb.appendCommand("commands/GetMachineMemoryCommand.groovy", "GetMachineMemoryCommand");
cb.start(new LiferayConsoleOutputHandler(out));


