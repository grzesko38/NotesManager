package pl.arczynskiadam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.arczynskiadam.web.form.LoginForm;

@Controller
public class LoginController extends AbstractController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(@ModelAttribute("loginForm") LoginForm form, Model model) {
		return "login";
	}
	
//	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
//	public String login(BindingResult result, Model model) {
//		return "login";
//	}
}
