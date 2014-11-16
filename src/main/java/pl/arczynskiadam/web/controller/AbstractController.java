package pl.arczynskiadam.web.controller;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

public abstract class AbstractController {

	@Autowired
    private ServletContext ctx;
	
	protected void createBreadcrumpAndSaveToModel(Model model, BreadcrumbsItem... items) {
		ArrayList<BreadcrumbsItem> navItems = new ArrayList<BreadcrumbsItem>();
		
		for (BreadcrumbsItem item : items) {
			item.setUrl(ctx.getContextPath() + item.getUrl());
			navItems.add(item);
		}
		
		model.addAttribute(GlobalControllerConstants.ModelAttrKeys.Navigation.Breadcrumbs, navItems);
	}
}
