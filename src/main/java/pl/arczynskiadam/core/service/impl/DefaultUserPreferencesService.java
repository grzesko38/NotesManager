package pl.arczynskiadam.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.arczynskiadam.core.dao.UserPreferencesRepository;
import pl.arczynskiadam.core.model.UserPreferencesModel;
import pl.arczynskiadam.core.service.UserPreferencesService;

@Service("userPreferencesService")
public class DefaultUserPreferencesService implements UserPreferencesService {

	@Autowired(required = true)
	UserPreferencesRepository userPreferencesDao;
	
	@Resource(name = "themes")
	List<String> themes;
	
	@Resource(name = "locales")
	List<String> locales;
	
	@Override
	public UserPreferencesModel getUserPreferencesForUser(int userId) {
		return userPreferencesDao.findPreferencesForUser();
	}

	@Override
	public UserPreferencesModel buildDefaultUserPreferences() {
		UserPreferencesModel prefs = new UserPreferencesModel();
		prefs.setLocale(locales.get(0));
		prefs.setTheme(themes.get(0));
		return prefs;
	}

}
