package pl.arczynskiadam.web.tag;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DateTag extends SimpleTagSupport {
	{
		separator = "-";
	}
	
	private String separator;

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
	
	@Override
    public void doTag() throws JspException, IOException {
        try {
        	Date now = new Date();
        	
        	String date = String.format("%02d" ,now.getDate()) + separator
        			+ String.format("%02d" ,now.getMonth() + 1) + separator
        			+ (now.getYear() + 1900);
        	
            getJspContext().getOut().write(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SkipPageException("Exception in formatting date");
        }
    }
}
