/* Cluster monitor prepares a set of Commands and executes them in cluster */

out.print("Starting...");

sbCommand = new CommandBuilder("https://raw.github.com/dsanz/scripts/cache/symp-2013/", true);
sbCommand.appendCommand("MemoryUsageCommand.groovy", "MemoryUsageCommand");
sbCommand.appendCommand("GetPortalImplManifestAttrsCommand.groovy", "GetPortalImplManifestAttrsCommand");
sbCommand.start();

while (!cl.isDone()) {
	Thread.sleep(50);
}
out.print("done!<br>");
out.print("<div id='clustermonitorresult'></div>");
out.println("<script src='https://raw.github.com/padolsey/prettyPrint.js/master/prettyprint.js'></script>");
out.println("<script type='text/javascript'>");
out.println("var r=" + cl.getResultAsString() + ";");
out.println("var tbl = prettyPrint(r);");
out.println("document.getElementById('clustermonitorresult').appendChild(tbl);");
out.println("</script>");

