package pl.arczynskiadam.web.controller;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import pl.arczynskiadam.web.tag.navigation.NavigationItem;

public class AbstractController {

	@Autowired
    private ServletContext ctx;
	
	protected void createNavigationAndSaveToModel(Model model, NavigationItem... items) {
		ArrayList<NavigationItem> navItems = new ArrayList<NavigationItem>();
		
		for (NavigationItem item : items) {
			item.setUrl(ctx.getContextPath() + item.getUrl());
			navItems.add(item);
		}
		
		model.addAttribute(GlobalControllerConstants.ModelAttrKeys.Navigation.navItems, navItems);
	}
}
