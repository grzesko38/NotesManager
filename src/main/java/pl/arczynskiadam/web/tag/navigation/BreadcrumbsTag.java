package pl.arczynskiadam.web.tag.navigation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class BreadcrumbsTag extends SimpleTagSupport {
	
	private static final Logger log = Logger.getLogger(BreadcrumbsTag.class);

	ArrayList<BreadcrumbsItem> items;
	
	public BreadcrumbsTag() {
		items = new ArrayList<BreadcrumbsItem>();
	}
	
	public ArrayList<BreadcrumbsItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<BreadcrumbsItem> items) {
		this.items = items;
	}

	@Override
    public void doTag() throws JspException, IOException {
        try {
        	StringBuilder sb = new StringBuilder();
        	Iterator<BreadcrumbsItem> it = items.iterator();
        	while (it.hasNext()) {
        		BreadcrumbsItem item = it.next();
        		if (it.hasNext()) {
        			sb.append("<span class=\"navitem\"><a href=\"")
        			.append(item.getUrl())
        			.append("\">")
        			.append(item.getName())
        			.append("</a></span>")
        			.append("<span class=\"navitem\">&nbsp;&gt;&nbsp;</a></span>");
        		} else {
        			sb.append("<span class=\"navitem\">")
        			.append(item.getName())
        			.append("</a></span>");
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
