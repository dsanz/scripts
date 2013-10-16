package core.resulthandlers;

import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter
import com.liferay.portal.kernel.log.Log
import com.liferay.portal.kernel.log.LogFactoryUtil;

public class LiferayConsoleOutputHandler implements ResultHandler {
	Log _log = LogFactoryUtil.getLog("LiferayConsoleOutputHandler") ;
	UnsyncPrintWriter _out;
	boolean _done;

	public LiferayConsoleOutputHandler(UnsyncPrintWriter out) {
		_out = out;
		_done = false;
	}

	public boolean isSynchronous() {
		return true;
	}

	public boolean isDone() {
		return _done;
	}

	public void done(CommandResultListener crl) {
		_log.error("Generating pretty-printed output for Scripting Console out writer");
		_out.print("<br>All command results have been received. Pretty-printing...<br>");
		_out.print("<div id='clustermonitorresult'></div>");
		_out.println("<script src='https://raw.github.com/padolsey/prettyPrint.js/master/prettyprint.js'></script>");
		_out.println("<script type='text/javascript'>");
		_out.println("var r=" + crl.getResultAsString() + ";");
		_out.println("var tbl = prettyPrint(r, {expand:false});");
		_out.println("document.getElementById('clustermonitorresult').appendChild(tbl);");
		_out.println("</script>");
		_done = true;
	}
}