package pl.arczynskiadam.web.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class DateFilterForm {
	
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	private LocalDate from;
	
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
	private LocalDate to;

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}
}
