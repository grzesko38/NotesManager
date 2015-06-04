package pl.arczynskiadam.web.tag.navigation;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

public class SortHeaderTag extends SimpleTagSupport {

	private static final Logger log = Logger.getLogger(SortHeaderTag.class);
	
	String sortColumn;
	String ascImgUrl;
	String descImgUrl;
	String divClass;
	Integer imgSize;
	
	public void doTag() throws JspException, IOException {
		StringWriter sw = new StringWriter();
		getJspBody().invoke(sw);
		
		String divOpen = String.format("<div class=\"%s\">", divClass);
		String divClose = "</div>"; 
		
		String sortAsc = String.format("<a href=\"?col=%s&asc=true\">"
				+ "<img src=\"%s\" width=\"%d\" height=\"%d\"/>"
				+ "</a>", sortColumn, ascImgUrl, imgSize, imgSize);
		
		String sortDesc = String.format("<a href=\"?col=%s&asc=false\">"
				+ "<img src=\"%s\" width=\"%d\" height=\"%d\"/>"
				+ "</a>", sortColumn, descImgUrl, imgSize, imgSize);
		
		StringBuilder sb = new StringBuilder();
		sb.append(divOpen)
		   .append(sw.toString())
		   .append(sortAsc)
		   .append(sortDesc)
		   .append(divClose);

		log.debug("writting output: " + sb.toString());
		getJspContext().getOut().println(sb.toString());
    }
	
	public String getSortColumn() {
		return sortColumn;
	}
	
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getAscImgUrl() {
		return ascImgUrl;
	}

	public void setAscImgUrl(String ascImgUrl) {
		this.ascImgUrl = ascImgUrl;
	}

	public String getDescImgUrl() {
		return descImgUrl;
	}

	public void setDescImgUrl(String descImgUrl) {
		this.descImgUrl = descImgUrl;
	}

	public Integer getImgSize() {
		return imgSize;
	}
	
	public void setImgSize(Integer imgSize) {
		this.imgSize = imgSize;
	}

	public String getDivClass() {
		return divClass;
	}

	public void setDivClass(String divClass) {
		this.divClass = divClass;
	}
}