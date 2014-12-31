package pl.arczynskiadam.web.messages;

import static pl.arczynskiadam.web.controller.GlobalControllerConstants.ModelAttrKeys.Messages.INFO_MSG;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class GlobalMessages {
	public static void addInfoFlashMessage(String code, Collection<String> params, RedirectAttributes attrs) {
		GlobalMessage msg = new GlobalMessage(code, params);
		if (attrs.containsAttribute(INFO_MSG)) {
			List<GlobalMessage> mesages = (List<GlobalMessage>) attrs.getFlashAttributes().get(INFO_MSG);
			mesages.add(msg);
		} else {
			attrs.addFlashAttribute(INFO_MSG, Collections.singletonList(msg));
		}
	}
}
