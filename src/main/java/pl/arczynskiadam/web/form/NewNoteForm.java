package pl.arczynskiadam.web.form;

import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import pl.arczynskiadam.web.form.validation.FirstCharsUpperCase;

public class NewNoteForm {

	@Size(min = 3, max = 20, message = "{note.add.nick.length.incorrect}")
	@FirstCharsUpperCase(count = 1, groups = {Extended.class}, message = "{note.add.uppercase}")
	private String author;
	
	@NotEmpty(groups = {Default.class}, message = "{validation.common.required}")
	@Size(max = 50, message = "{note.add.title.length.incorrect}")
	private String title;
	
	@NotEmpty(groups = {Default.class}, message = "{validation.common.required}")
	@Size(max = 4000, message = "{note.add.content.length.incorrect}")
	private String content;
	
	@NotNull(message = "{DateTimeFormat.dateForm.date}")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date deadline;
	
	private Float longitude = null;
	private Float latitude = null;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String nick) {
		this.author = nick;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}


	public interface Extended {}
	
	@GroupSequence({Default.class, Extended.class})
	public interface All {}
}
