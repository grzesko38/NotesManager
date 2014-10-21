package pl.arczynskiadam.web.controller;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import pl.arczynskiadam.web.tag.navigation.NavigationItem;

public abstract class AbstractController {

	@Autowired
    private ServletContext ctx;
	
	protected void createNavigationAndSaveToModel(Model model, NavigationItem... items) {
		ArrayList<NavigationItem> navItems = new ArrayList<NavigationItem>();
		
		NavigationItem prev = null;
		for (NavigationItem item : items) {
			if (prev == null) {
				item.setUrl(ctx.getContextPath() + item.getUrl());
			} else {
				item.setUrl(prev.getUrl() + item.getUrl());
			}
			navItems.add(item);
			prev = item;
		}
		
		model.addAttribute(GlobalControllerConstants.ModelAttrKeys.Navigation.navItems, navItems);
	}
}
