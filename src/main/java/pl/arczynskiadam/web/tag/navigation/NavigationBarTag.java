package pl.arczynskiadam.web.tag.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NavigationBarTag extends SimpleTagSupport {
	
	private static final String URL_SEPARATOR = "/";
	ArrayList<NavigationItem> navigationItems;
	
	public NavigationBarTag() {
		navigationItems = new ArrayList<NavigationItem>();
	}
	
	public ArrayList<NavigationItem> getNavigationItems() {
		return navigationItems;
	}

	public void setNavigationItems(ArrayList<NavigationItem> navigationItems) {
		this.navigationItems = navigationItems;
	}

	@Override
    public void doTag() throws JspException, IOException {
        try {
        	StringBuilder sb = new StringBuilder();
        	Iterator<NavigationItem> it = navigationItems.iterator();
        	while (it.hasNext()) {
        		NavigationItem item = it.next();
        		if (it.hasNext()) {
        			sb.append("<a href=\"").append(item.getUrl()).append("\">").append(item.getName()).append("</a>").append(" / ");
        		} else {
        			sb.append(item.getName());
        		}
        	}
            getJspContext().getOut().write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SkipPageException("Exception in formatting date");
        }
    }
}
