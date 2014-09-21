package pl.arczynskiadam.web.form.note;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import pl.arczynskiadam.web.form.note.NewNoteForm.Extended;
import pl.arczynskiadam.web.validation.FieldMatch;
import pl.arczynskiadam.web.validation.note.FirstCharsUpperCase;

@FieldMatch(groups = {Extended.class}, fieldSource = "email", fieldConfirm = "emailConfirmation")
public class NewNoteForm {

	@NotEmpty(groups = {Default.class})
	@FirstCharsUpperCase(count=1, groups = {Extended.class})
	private String author;

	@NotEmpty(groups = {Default.class})
	@Email(groups = {Default.class})
	private String email;
	
	@NotEmpty(groups = {Default.class})
	private String emailConfirmation;
	
	@NotEmpty(groups = {Default.class})
	private String content;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String nick) {
		this.author = nick;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailConfirmation() {
		return emailConfirmation;
	}
	public void setEmailConfirmation(String emailConfirmation) {
		this.emailConfirmation = emailConfirmation;
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
