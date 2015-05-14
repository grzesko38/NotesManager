package pl.arczynskiadam.web.facade;

public interface UserPreferencesFacade {
	public void setPreferredThemeForCurrentUser(String themeName);
	public void setPreferredLocaleForCurrentUser(String locale);
}
