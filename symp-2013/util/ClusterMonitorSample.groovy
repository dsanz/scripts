package util

import core.builders.CommandBuilder
import core.resulthandlers.LiferayConsoleOutputHandler;

out.print("Starting...");

boolean isCluster=true;

cb = new CommandBuilder("https://raw.github.com/dsanz/scripts/master/symp-2013/", isCluster);
cb.appendCommand("commands/MemoryUsageCommand.groovy", "MemoryUsageCommand");
cb.appendCommand("commands/GetPortalImplManifestAttrsCommand.groovy", "GetPortalImplManifestAttrsCommand");
cb.appendCommand("commands/GetMachineMemoryCommand.groovy", "GetMachineMemoryCommand");
cb.appendCommand("commands/GetJVMInfoCommand.groovy", "GetJVMInfoCommand");
cb.appendCommand("commands/GetCPUInfoCommand.groovy", "GetCPUInfoCommand");
cb.start(new LiferayConsoleOutputHandler(out));


