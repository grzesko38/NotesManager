package pl.arczynskiadam.web.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DateFilterForm {
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date from;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date to;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
