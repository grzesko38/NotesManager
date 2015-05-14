package pl.arczynskiadam.core.service;

import pl.arczynskiadam.core.model.UserPreferencesModel;

public interface UserPreferencesService {
	public UserPreferencesModel getUserPreferencesForUser(int userId);
	public UserPreferencesModel buildDefaultUserPreferences();
}
