package pl.arczynskiadam.web.form.note;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DateForm {
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
