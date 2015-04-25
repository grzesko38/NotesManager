package pl.arczynskiadam.security.impl;

import pl.arczynskiadam.web.SecurityConstants;

public class EncodingHelper {

	public static String buildPlainText(String plainPassword, String salt) {
		return salt.concat(SecurityConstants.DELIMITER).concat(plainPassword);
	}

}
