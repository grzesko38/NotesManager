package pl.arczynskiadam.web.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import pl.arczynskiadam.web.form.validation.EmailAvailable;
import pl.arczynskiadam.web.form.validation.FieldMatch;
import pl.arczynskiadam.web.form.validation.NickAvailable;

@FieldMatch.List({
	@FieldMatch(fieldSource = "password", fieldConfirm= "passwordConfirm", message = "{register.password.confirm.mismatch}"),
	@FieldMatch(fieldSource = "email", fieldConfirm= "emailConfirm", message = "{register.email.confirm.mismatch}")
})
public class RegisterForm {
	
	@NotBlank(message = "{register.nick.length.incorrect}")
	@Size(min = 3, max = 20, message = "{register.nick.length.incorrect}")
	@NickAvailable(message = "{register.nick.unavailable}")
	private String nick;
	
	@NotBlank(message = "{register.password.length.incorrect}")
	@Size(min = 8, message = "{register.password.length.incorrect}")
	private String password;
	
	@NotBlank(message = "{validation.common.required}")
	private String passwordConfirm;
	
	@NotBlank(message = "{validation.common.email.incorrect}")
	@Pattern(regexp = "^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$", message = "{validation.common.email.incorrect}")
	@EmailAvailable(message = "{register.email.unavailable}")
	private String email;
	
	@NotBlank(message = "{validation.common.required}")
	private String emailConfirm;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailConfirm() {
		return emailConfirm;
	}

	public void setEmailConfirm(String emailConfirm) {
		this.emailConfirm = emailConfirm;
	}
}
