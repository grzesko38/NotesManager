package pl.arczynskiadam.web.messages;

import static pl.arczynskiadam.web.controller.GlobalControllerConstants.ModelAttrKeys.Messages.INFO_MSG;
import static pl.arczynskiadam.web.controller.GlobalControllerConstants.ModelAttrKeys.Messages.WARN_MSG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class GlobalMessages {
	public static void addInfoFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		addFlashMsg(INFO_MSG, code, params, attrs);
	}
	
	public static void addInfoMessage(String code, Collection<String> params, Model model) {
		addMsg(INFO_MSG, code, params, model);
	}
	
	public static void addWarningFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		addFlashMsg(WARN_MSG, code, params, attrs);
	}
	
	public static void addWarningMessage(String code, Collection<String> params, Model model) {
		addMsg(WARN_MSG, code, params, model);
	}
	
	private static void addFlashMsg(String type, String code, Collection<String> params, RedirectAttributes attrs) {
		GlobalMessage msg = new GlobalMessage(code, params);
		if (attrs.containsAttribute(type)) {
			List<GlobalMessage> mesages = (List<GlobalMessage>) attrs.getFlashAttributes().get(type);
			mesages.add(msg);
		} else {
			attrs.addFlashAttribute(type, Collections.singletonList(msg));
		}
	}
	
	private static void addMsg(String type, String code, Collection<String> params, Model model) {
		GlobalMessage msg = new GlobalMessage(code, params);
		if (model.containsAttribute(type)) {
			List<GlobalMessage> mesages = (List<GlobalMessage>) model.asMap().get(type);
			mesages.add(msg);
		} else {
			model.addAttribute(type, Collections.singletonList(msg));
		}
	}
}
