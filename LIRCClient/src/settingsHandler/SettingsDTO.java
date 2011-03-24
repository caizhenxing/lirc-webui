package settingsHandler;

import java.util.UUID;

public class SettingsDTO {
	
	public String url;
	public String id;
	boolean defaultSD;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {

		if (id == null || id.length() == 0) {
			UUID uuid = UUID.randomUUID();
			id = uuid.toString();
		}

		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDefaultSD() {
		return defaultSD;
	}

	public void setDefaultSD(boolean defaultSD) {
		this.defaultSD = defaultSD;
	}

}
