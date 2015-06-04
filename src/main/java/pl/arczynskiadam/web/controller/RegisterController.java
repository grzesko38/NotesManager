package pl.arczynskiadam.web.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.arczynskiadam.core.exception.EmailUnavailableException;
import pl.arczynskiadam.core.exception.NickUnavailableException;
import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.LoginControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.controller.constants.RegisterControllerConstants;
import pl.arczynskiadam.web.facade.UserFacade;
import pl.arczynskiadam.web.form.RegisterForm;
import pl.arczynskiadam.web.messages.GlobalMessage;
import pl.arczynskiadam.web.messages.GlobalMessages;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

@Controller
public class RegisterController extends AbstractController {

	@Resource
	UserFacade userFacade;
	
	@RequestMapping(value = RegisterControllerConstants.URLs.REGISTER, method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute(RegisterControllerConstants.ModelAttrKeys.Form.Register, new RegisterForm());
		
		addDefaultBreadcrumbsToModel(model);
		
		return RegisterControllerConstants.Pages.REGISTER;
	}
	
	@RequestMapping(value = RegisterControllerConstants.URLs.REGISTER, method = RequestMethod.POST)
	public String performRegister(@Valid @ModelAttribute(RegisterControllerConstants.ModelAttrKeys.Form.Register) RegisterForm form,
			BindingResult result,
			Model model,
			RedirectAttributes attrs) {
		
		if (result.hasErrors()) {
			addDefaultBreadcrumbsToModel(model);
			GlobalMessages.addErrorMessage("global.error.correctAll", model);
			return RegisterControllerConstants.Pages.REGISTER;
		}
		
		userFacade.registerUser(form.getNick(), form.getEmail(), form.getPassword());
		GlobalMessages.addInfoFlashMessage("global.register.success", attrs);
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_NOTES_FULL;
	}
	
	private void addDefaultBreadcrumbsToModel(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", LoginControllerConstants.URLs.LOGIN),
				new BreadcrumbsItem("Register", GlobalControllerConstants.Misc.HASH));
	}
}
