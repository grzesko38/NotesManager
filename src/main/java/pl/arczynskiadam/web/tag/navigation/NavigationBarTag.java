package pl.arczynskiadam.web.tag.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class NavigationBarTag extends SimpleTagSupport {
	
	private static final Logger log = Logger.getLogger(NavigationBarTag.class);

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
        			sb.append("<a href=\"")
        			.append(item.getUrl())
        			.append("\">")
        			.append(item.getName())
        			.append("</a>")
        			.append(" &gt; ");
        		} else {
        			sb.append(item.getName());
        		}
        	}
        	
        	log.debug("writting output: " + sb.toString());
            getJspContext().getOut().write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SkipPageException("Exception in formatting date");
        }
    }
}
