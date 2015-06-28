package pl.arczynskiadam.web.messages;

import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Messages.ERR_MSG;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Messages.INFO_MSG;
import static pl.arczynskiadam.web.controller.constants.GlobalControllerConstants.ModelAttrKeys.Messages.WARN_MSG;

import java.util.Collection;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

public class GlobalMessages {
	public static void addInfoMessage(String code, Model model) {
		addMsg(INFO_MSG, code, null, model);
	}
	
	public static void addInfoMessage(String code, Collection<String> params, Model model) {
		addMsg(INFO_MSG, code, params, model);
	}
	
	public static void addInfoFlashMessage(String code, RedirectAttributes attrs) {
		addFlashMsg(INFO_MSG, code, null, attrs);
	}
	
	public static void addInfoFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		addFlashMsg(INFO_MSG, code, params, attrs);
	}
	
	public static void addWarningMessage(String code, Model model) {
		addMsg(WARN_MSG, code, null, model);
	}
	
	public static void addWarningMessage(String code, Collection<String> params, Model model) {
		addMsg(WARN_MSG, code, params, model);
	}
	
	public static void addWarningFlashMessage(String code, RedirectAttributes attrs) {
		addFlashMsg(WARN_MSG, code, null, attrs);
	}
	
	public static void addWarningFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		addFlashMsg(WARN_MSG, code, params, attrs);
	}
	
	public static void addErrorMessage(String code, Model model) {
		addMsg(ERR_MSG, code, null, model);
	}
	
	public static void addErrorMessage(String code, Collection<String> params, Model model) {
		addMsg(ERR_MSG, code, params, model);
	}
	
	public static void addErrorFlashMessage(String code, RedirectAttributes attrs) {
		addFlashMsg(ERR_MSG, code, null, attrs);
	}
	
	public static void addErrorFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		addFlashMsg(ERR_MSG, code, params, attrs);
	}
	
	private static void addFlashMsg(String type, String code, Collection<String> params, RedirectAttributes attrs) {
		GlobalMessage msg = new GlobalMessage(code, params);
		if (attrs.getFlashAttributes().containsKey(type)) {
			List<GlobalMessage> mesages = (List<GlobalMessage>) attrs.getFlashAttributes().get(type);
			mesages.add(msg);
		} else {
			attrs.addFlashAttribute(type, Lists.newArrayList(msg));
		}
	}
	
	private static void addMsg(String type, String code, Collection<String> params, Model model) {
		GlobalMessage msg = new GlobalMessage(code, params);
		if (model.containsAttribute(type)) {
			List<GlobalMessage> mesages = (List<GlobalMessage>) model.asMap().get(type);
			mesages.add(msg);
		} else {
			model.addAttribute(type, Lists.newArrayList(msg));
		}
	}
}
