package pl.arczynskiadam.core.model.note;

import java.util.Date;

public class NoteDetailsDTO extends NoteDTO {
	
	private Date dateCreated;
	private String content;
	
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "NoteDetailsDTO [id=" + id + ", author=" + author + ", email=" + email
				+ ", dateCreated=" + dateCreated + "dateCreated=" + dateCreated + ", content="
				+ content + "]";
	}
}
