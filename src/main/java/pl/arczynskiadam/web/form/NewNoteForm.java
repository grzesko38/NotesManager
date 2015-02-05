package pl.arczynskiadam.web.form;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotEmpty;

import pl.arczynskiadam.web.form.validation.FirstCharsUpperCase;

public class NewNoteForm {

	@NotEmpty(groups = {Default.class}, message = "{note.required}")
	@FirstCharsUpperCase(count = 1, groups = {Extended.class}, message = "{note.uppercase}")
	private String author;
	
	@NotEmpty(groups = {Default.class}, message = "{note.required}")
	private String content;
	
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
	
	public interface Extended {}
	
	@GroupSequence({Default.class, Extended.class})
	public interface All {}
}
