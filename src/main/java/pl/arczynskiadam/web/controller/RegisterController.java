package pl.arczynskiadam.web.controller;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.Prefixes.FORWARD_PREFIX;
import static pl.arczynskiadam.web.controller.constants.NoteControllerConstants.URLs.SHOW_NOTES_FULL;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.arczynskiadam.web.controller.constants.GlobalControllerConstants;
import pl.arczynskiadam.web.controller.constants.LoginControllerConstants;
import pl.arczynskiadam.web.controller.constants.NoteControllerConstants;
import pl.arczynskiadam.web.controller.constants.RegisterControllerConstants;
import pl.arczynskiadam.web.facade.NoteFacade;
import pl.arczynskiadam.web.facade.UserFacade;
import pl.arczynskiadam.web.form.RegisterForm;
import pl.arczynskiadam.web.messages.GlobalMessages;
import pl.arczynskiadam.web.tag.navigation.BreadcrumbsItem;

@Controller
public class RegisterController extends AbstractController {

	@Resource
	private UserFacade userFacade;
	
	@Resource
	private NoteFacade noteFacade;
	
	@Resource(name = "authenticationManager")
	private AuthenticationManager authMgr;
	
	@Resource(name = "userDetailsService")
	private UserDetailsService userDetailsService;
	
	private static final Logger log = Logger.getLogger(RegisterController.class);
	
	@RequestMapping(value = RegisterControllerConstants.URLs.REGISTER, method = RequestMethod.GET)
	public String register(Model model) {
		if (isUserLoggedIn()) {
			return FORWARD_PREFIX + SHOW_NOTES_FULL;
		}
		
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
		
		autoLogin(form, model, attrs);
		
		return GlobalControllerConstants.Prefixes.REDIRECT_PREFIX + NoteControllerConstants.URLs.SHOW_NOTES_FULL;
	}

	private void autoLogin(RegisterForm form, Model model, RedirectAttributes attrs) {
		try {
			UserDetails userDetails = userDetailsService.loadUserByUsername(form.getNick());
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
					userDetails, form.getPassword(),
					userDetails.getAuthorities());
			
			authMgr.authenticate(auth);

			if (auth.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(auth);
				noteFacade.removePaginationDataFromSession();
				GlobalMessages.addInfoFlashMessage("global.login.auto", attrs);
			}
		} catch (Exception e) {
			log.error("Autologin failed");
		}
	}
	
	private void addDefaultBreadcrumbsToModel(Model model) {
		createBreadcrumpAndSaveToModel(model,
				new BreadcrumbsItem("Home", LoginControllerConstants.URLs.LOGIN),
				new BreadcrumbsItem("Register", GlobalControllerConstants.Misc.HASH));
	}
}
