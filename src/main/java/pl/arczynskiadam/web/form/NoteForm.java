package pl.arczynskiadam.web.form;

import java.time.LocalDate;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import pl.arczynskiadam.web.form.validation.FirstCharsUpperCase;

public class NoteForm {

	private Integer id;
	
	@Size(min = 3, max = 20, message = "{note.add.nick.length.incorrect}")
	@FirstCharsUpperCase(count = 1, groups = {Extended.class}, message = "{note.add.uppercase}")
	private String author;
	
	@NotBlank(groups = {Default.class}, message = "{validation.common.required}")
	@Size(max = 50, message = "{note.add.title.length.incorrect}")
	private String title;
	
	@NotBlank(groups = {Default.class}, message = "{validation.common.required}")
	@Size(max = 4000, message = "{note.add.content.length.incorrect}")
	private String content;
	
	@NotNull(message = "{validation.common.required}")
	@DateTimeFormat(iso = ISO.DATE, pattern="dd/MM/yyyy")
//	@Future(message = "{note.add.deadline.future.incorrect}")
	private LocalDate deadline;
	
	private Double longitude = null;
	private Double latitude = null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
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
	
	public LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}


	public interface Extended {}
	
	@GroupSequence({Default.class, Extended.class})
	public interface All {}
}
