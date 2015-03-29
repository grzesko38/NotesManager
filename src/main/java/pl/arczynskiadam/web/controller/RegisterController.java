package pl.arczynskiadam.web.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.LoginControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.controller.constants.RegisterControllerConstants;
import pl.arczynskiadam.web.form.RegisterForm;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

@Controller
public class RegisterController extends AbstractController {

	@RequestMapping(value = RegisterControllerConstants.URLs.REGISTER, method = RequestMethod.GET)
	public String register(Model model) {
		model.addAttribute(RegisterControllerConstants.ModelAttrKeys.Form.Register, new RegisterForm());
		
		addDefaultBreadcrumbsToModel(model);
		
		return RegisterControllerConstants.Pages.REGISTER;
	}
	
	@RequestMapping(value = RegisterControllerConstants.URLs.REGISTER, method = RequestMethod.POST)
	public String performRegister(@Valid @ModelAttribute(RegisterControllerConstants.ModelAttrKeys.Form.Register) RegisterForm form,
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			addDefaultBreadcrumbsToModel(model);
			return RegisterControllerConstants.Pages.REGISTER;
		}
		
		return GlobalControllerConstants.Prefixes.REDIRECT + NoteControllerConstants.URLs.SHOW_FULL;
	}
	
	private void addDefaultBreadcrumbsToModel(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", LoginControllerConstants.URLs.LOGIN),
				new BreadcrumbsItem("Register", GlobalControllerConstants.Misc.HASH));
	}
}
