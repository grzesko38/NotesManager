package pl.arczynskiadam.web.tag.navigation;

public class NavigationItem {
	private String name;
	private String url;
	
	public NavigationItem(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}