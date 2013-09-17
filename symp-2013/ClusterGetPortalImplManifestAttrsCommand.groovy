import java.util.jar.Attributes
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import java.util.jar.Manifest
import com.liferay.portal.util.PortalUtil;

public class ClusterGetPortalImplManifestAttrsCommand extends ClusterCommand {
	public String PORTAL_LIB_NAME = "portal-impl";
	public String PORTAL_LIB_PATH =  PORTAL_LIB_NAME + ".jar";

	public ClusterGetPortalImplManifestAttrsCommand() {
		super("ClusterGetPortalImplManifestAttrsCommand");
	}

	public void execute() {
		File portalImplManifest = new File(PortalUtil.getPortalLibDir() + "/" +
			PORTAL_LIB_PATH + "/META-INF/MANIFEST.MF");

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(portalImplManifest);
			Attributes attributes = new Manifest(fis).getMainAttributes();
			for (Object attrName : attributes.keySet()) {
				addResult((String)attrName, attributes.getValue(attrName));
			}
		}
		finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
}

new ClusterGetPortalImplManifestAttrsCommand().run();