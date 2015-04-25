package pl.arczynskiadam.security.impl;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

public class SaltGenerator {
	public static String generateRandomSalt(int bytes) {
		BytesKeyGenerator generator = KeyGenerators.secureRandom(bytes);
		byte[] salt = generator.generateKey();
		return new String(Hex.encode(salt));
	}
}
