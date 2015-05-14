package pl.arczynskiadam.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.arczynskiadam.core.service.UserService;
import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.LoginControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.form.LoginForm;
import pl.arczynskiadam.web.messages.GlobalMessages;

@Controller
public class LoginController extends AbstractController {
	
	@RequestMapping(value = LoginControllerConstants.URLs.LOGIN, method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@ModelAttribute(LoginControllerConstants.ModelAttrKeys.Form.Login) LoginForm loginForm,
			Model model) {

		if (error != null) {
			GlobalMessages.addErrorMessage("login.failed", model);
		}

		return LoginControllerConstants.Pages.LOGIN;
	}
	
	@RequestMapping(value = LoginControllerConstants.URLs.LOGOUT, method = RequestMethod.GET)
	public String logout(RedirectAttributes attrs) {

		GlobalMessages.addInfoFlashMessage("global.logout.success", attrs);
		GlobalMessages.addInfoFlashMessage("global.continueAsAnonymous", attrs);
		
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
}
