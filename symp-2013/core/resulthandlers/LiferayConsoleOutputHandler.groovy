import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;

public class LiferayConsoleOutputHandler implements ResultHandler {
	UnsyncPrintWriter _out;

	public LiferayConsoleOutputHandler(UnsyncPrintWriter out) {
		_out = out;
	}

	public void done(CommandResultListener crl) {
		_out.print("<br>All command results have been received. Pretty-printing...<br>");
		_out.print("<div id='clustermonitorresult'></div>");
		_out.println("<script src='https://raw.github.com/padolsey/prettyPrint.js/master/prettyprint.js'></script>");
		_out.println("<script type='text/javascript'>");
		_out.println("var r=" + crl.getResultAsString() + ";");
		_out.println("var tbl = prettyPrint(r);");
		_out.println("document.getElementById('clustermonitorresult').appendChild(tbl);");
		_out.println("</script>");
	}
}