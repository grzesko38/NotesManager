package pl.arczynskiadam.web.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DateForm {
	
	@NotNull(message = "{DateTimeFormat.dateForm.date}")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
