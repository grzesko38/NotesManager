package pl.arczynskiadam.web.form;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotEmpty;

import pl.arczynskiadam.web.form.validation.FirstCharsUpperCase;

public class NewNoteForm {

	@Size(min = 3, max = 20, message = "{note.add.nick.length.incorrect}")
	@FirstCharsUpperCase(count = 1, groups = {Extended.class}, message = "{note.add.uppercase}")
	private String author;
	
	@Size(max = 50, message = "{note.add.title.length.incorrect}")
	private String title;
	
	@NotEmpty(groups = {Default.class}, message = "{validation.common.required}")
	@Size(max = 4000, message = "{note.add.content.length.incorrect}")
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
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	public interface Extended {}
	
	@GroupSequence({Default.class, Extended.class})
	public interface All {}
}
