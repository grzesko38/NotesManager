package pl.arczynskiadam.web.data;

import java.util.Date;

public class DateFilterData {
	private Date from;
	private Date to;
	
	public DateFilterData() {}
	
	public DateFilterData(Date from, Date to) {
		this.from = from;
		this.to = to;
	}
	
	public boolean isActive() {
		return !(from == null && to == null);
	}
	
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
