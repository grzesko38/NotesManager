package pl.arczynskiadam.web.controller;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Navigation.BREADCRUMBS;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.ModelAttrKeys.View.PAGINATION;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

public abstract class AbstractController {

	@Autowired
    private ServletContext ctx;
	
	@Resource(name = "themes")
	List<String> themes;
	
	@Resource(name = "locales")
	List<String> locales;
	
	protected void createBreadcrumpAndSaveToModel(Model model, BreadcrumbsItem... items) {
		ArrayList<BreadcrumbsItem> navItems = new ArrayList<BreadcrumbsItem>();
		
		for (BreadcrumbsItem item : items) {
			item.setUrl(ctx.getContextPath() + item.getUrl());
			navItems.add(item);
		}
		
		model.addAttribute(BREADCRUMBS, navItems);
	}
	
	@ModelAttribute(value = "userName")
	public String getUserName(Principal principal) {
		return principal != null ? principal.getName() : "anonymous";
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
