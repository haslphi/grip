package at.jku.se.grip.security;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CryptoService {

	public static final String AES = "AES";
	private static CryptoService service = new CryptoService();

	public static CryptoService getInstance() {
		return service;
	}

	/**
	 * encrypt a value
	 *
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public String encrypt(String value, String cryptoKey) throws GeneralSecurityException {
		if (StringUtils.isBlank(value)) {
			return value;
		}
		SecretKeySpec sks = getSecretKeySpec(cryptoKey);
		Cipher cipher = Cipher.getInstance(AES);
		cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
		byte[] encrypted = cipher.doFinal(value.getBytes());
		return byteArrayToHexString(encrypted);
	}

	/**
	 * decrypt a value
	 *
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public String decrypt(String value, String cryptoKey) throws GeneralSecurityException {
		if (StringUtils.isBlank(value)) {
			return value;
		}
		SecretKeySpec sks = getSecretKeySpec(cryptoKey);
		Cipher cipher = Cipher.getInstance(AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(value));
		return new String(decrypted);
	}

	private SecretKeySpec getSecretKeySpec(String cryptoKey) {
		byte[] key = hexStringToByteArray(cryptoKey);
		SecretKeySpec sks = new SecretKeySpec(key, AES);
		return sks;
	}

	private String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
	
	/**
	 * use to generate key
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance(AES);
		keyGen.init(128);
		SecretKey sk = keyGen.generateKey();

		byte[] b = sk.getEncoded();
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}

		System.out.println(sb.toString().toUpperCase());
	}
}
