package pl.arczynskiadam.web.data;

import java.time.LocalDate;

public class DateFilterData {
	private LocalDate from;
	private LocalDate to;
	
	public DateFilterData() {}
	
	public DateFilterData(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean isActive() {
		return !(from == null && to == null);
	}

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
