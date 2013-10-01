import java.util.jar.Attributes
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import java.util.jar.Manifest
import com.liferay.portal.util.PortalUtil;

public class GetPortalImplManifestAttrsCommand extends Command {
	public String PORTAL_LIB_NAME = "portal-impl";
	public String PORTAL_LIB_PATH =  PORTAL_LIB_NAME + ".jar";

	public GetPortalImplManifestAttrsCommand(boolean isCluster) {
		super("portal-impl_MF_Attrs", isCluster);
	}

	public void execute() {
		File portalImplManifest = new File(PortalUtil.getPortalLibDir() + "/" +
			PORTAL_LIB_PATH + "/META-INF/MANIFEST.MF");

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(portalImplManifest);
			Attributes attributes = new Manifest(fis).getMainAttributes();
			for (Object attrName : attributes.keySet()) {
				addResult((String)attrName, attributes.getValue(attrName).replace("\"", "'"));
			}
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}