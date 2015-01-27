package pl.arczynskiadam.web.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

	@NotEmpty
	@Size(min = 6, max = 20, message= "{login.nick.length.incorrect}")
	private String nick;
	
	@Size(min = 8, message= "{login.nick.length.incorrect}")
	private String password;
	
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
}
