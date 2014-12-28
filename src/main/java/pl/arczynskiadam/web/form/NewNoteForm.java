package pl.arczynskiadam.web.form;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import pl.arczynskiadam.web.form.NewNoteForm.Extended;
import pl.arczynskiadam.web.validation.FieldMatch;
import pl.arczynskiadam.web.validation.FirstCharsUpperCase;

@FieldMatch(groups = {Extended.class}, fieldSource = "email", fieldConfirm = "emailConfirmation", message = "{note.email.match}")
public class NewNoteForm {

	@NotEmpty(groups = {Default.class}, message = "{note.required}")
	@FirstCharsUpperCase(count = 1, groups = {Extended.class}, message = "{note.uppercase}")
	private String author;

	@NotEmpty(groups = {Default.class}, message = "{note.required}")
	@Email(groups = {Default.class}, message = "{note.email.invalid}")
	private String email;
	
	@NotEmpty(groups = {Default.class}, message = "{note.required}")
	private String emailConfirmation;
	
	@NotEmpty(groups = {Default.class}, message = "{note.required}")
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
