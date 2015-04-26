package pl.arczynskiadam.web.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import pl.arczynskiadam.web.form.validation.EmailAvailable;
import pl.arczynskiadam.web.form.validation.FieldMatch;
import pl.arczynskiadam.web.form.validation.NickAvailable;

@FieldMatch.List({
	@FieldMatch(fieldSource = "password", fieldConfirm= "passwordConfirm", message = "{validation.password.confirm}"),
	@FieldMatch(fieldSource = "email", fieldConfirm= "emailConfirm", message = "{validation.email.confirm}")
})
public class RegisterForm {
	
	@NotBlank(message = "{validation.common.required}")
	@Size(min = 3, max = 32, message = "{validation.common.length.minmax}")
	@NickAvailable(message = "{register.error.nick.unavailable}")
	private String nick;
	
	@NotBlank(message = "{validation.common.required}")
	@Size(min = 8, max = 32, message = "{validation.common.length.minmax}")
	private String password;
	
	@NotBlank(message = "{validation.common.required}")
	private String passwordConfirm;
	
	@NotBlank(message = "{validation.common.required}")
	@Pattern(regexp = "^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}$", message = "{validation.email.incorrect}")
	@EmailAvailable(message = "{register.error.email.unavailable}")
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
