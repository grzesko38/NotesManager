package pl.arczynskiadam.core.model.note;

import java.util.Date;

public class NoteDTO {

	protected int id;
	protected String author;
	protected String email;
	protected Date dateCreated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public String toString() {
		return "NoteDTO [id=" + id + ", author=" + author + ", email=" + email
				+ ", dateCreated=" + dateCreated + "]";
	}
}
