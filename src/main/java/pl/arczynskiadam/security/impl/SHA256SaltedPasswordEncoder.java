package pl.arczynskiadam.security.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.arczynskiadam.web.SecurityConstants;

@Component(value = "SHA256SaltedPasswordEncoder")
public class SHA256SaltedPasswordEncoder implements PasswordEncoder {
	
	@Override
	public String encode(CharSequence paramCharSequence) {
		MessageDigest md;
		try {
			
			md = MessageDigest.getInstance("SHA-256");
			md.update(paramCharSequence.toString().getBytes("UTF-8"));
			byte[] digest = md.digest();
			return new String(Hex.encode(digest));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean matches(CharSequence paramCharSequence, String paramString) {
		String[] saltAndHash = paramString.split(SecurityConstants.DELIMITER);
		String plain = saltAndHash[0].toLowerCase().concat(SecurityConstants.DELIMITER).concat(paramCharSequence.toString());
		return this.encode(plain).equals(saltAndHash[1]);
	}
}
