package blud.util;

public class Version {
	public final String
		VERSION_NAME;
	public final int
		VERSION_ID,
		RELEASE_ID,
		PATCH_ID;
	
	public Version(
			String version_name,
			int version_id,
			int release_id,
			int patch_id
			) {
		VERSION_NAME = version_name;
		VERSION_ID = version_id;
		RELEASE_ID = release_id;
		PATCH_ID = patch_id;
	}
	
	@Override
	public String toString() {
		return 
				VERSION_NAME + " " + 
				VERSION_ID + "." + 
				RELEASE_ID + "." + 
				PATCH_ID;
	}	
}
