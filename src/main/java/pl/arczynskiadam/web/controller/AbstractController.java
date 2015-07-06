package pl.arczynskiadam.web.controller;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Navigation.BREADCRUMBS_MODEL_ATTR;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

public abstract class AbstractController {

	@Autowired
    private ServletContext ctx;
	
	@Resource(name = "themes")
	protected List<String> themes;
	
	@Resource(name = "locales")
	protected List<String> locales;
	
	@Resource(name = "messageSource")
	protected MessageSource messageSource;
	
	protected void createBreadcrumpAndSaveToModel(Model model, BreadcrumbsItem... items) {
		ArrayList<BreadcrumbsItem> navItems = new ArrayList<BreadcrumbsItem>();
		
		for (BreadcrumbsItem item : items) {
			item.setUrl(ctx.getContextPath() + item.getUrl());
			navItems.add(item);
		}
		
		model.addAttribute(BREADCRUMBS_MODEL_ATTR, navItems);
	}
	
	protected boolean isUserLoggedIn() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return Optional.of(auth).map(a -> !(a instanceof AnonymousAuthenticationToken)).orElse(false);
	}
	
	protected String getMessage(String key) {
		return messageSource.getMessage(key, new Object[0], getCurrentLocale());
	}
	
	protected Locale getCurrentLocale() {
		return LocaleContextHolder.getLocale();
	}
	
	@ModelAttribute(value = "userName")
	public String getUserName(Principal principal) {
		return Optional.ofNullable(principal).map(Principal::getName).orElse("anonymous");
	}
	
	@ModelAttribute(value = "themes")
	public List<String> getThemes() {
		return themes;
	}
	
	@ModelAttribute(value = "locales")
	public List<String> getLocales() {
		return locales;
	}
}
