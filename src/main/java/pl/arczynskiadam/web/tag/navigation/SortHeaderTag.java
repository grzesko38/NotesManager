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
	Integer imgSize;
	
	public void doTag() throws JspException, IOException {
		StringWriter sw = new StringWriter();
		getJspBody().invoke(sw);
		
		String sortAsc = String.format("<a href=\"?col=%s&sort=asc\">"
				+ "<img src=\"%s\" width=\"%d\" height=\"%d\"/>"
				+ "</a>", sortColumn, ascImgUrl, imgSize, imgSize);
		
		String sortDesc = String.format("<a href=\"?col=%s&sort=desc\">"
				+ "<img src=\"%s\" width=\"%d\" height=\"%d\"/>"
				+ "</a>", sortColumn, descImgUrl, imgSize, imgSize);
		
		StringBuilder sb = new StringBuilder();
		sb.append(sw.toString()).append(sortAsc).append(sortDesc);

		log.debug("writting output: " + sb.toString());
		getJspContext().getOut().println(sb.toString());
    }
	
//	<span>${colLabel}</span>
//	<a href="?col=${colName}&sort=asc">
//		<img src="${pageContext.request.contextPath}/themes/<spring:theme code="img.sort.asc"/>"
//             	width="${imgSize}" height="${imgSize}"/>
//	</a>
//	<a href="?col=${colName}&sort=desc">
//		<img src="${pageContext.request.contextPath}/themes/<spring:theme code="img.sort.desc"/>"
//             	width="${imgSize}" height="${imgSize}"/>
//	</a>
	
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
	
}