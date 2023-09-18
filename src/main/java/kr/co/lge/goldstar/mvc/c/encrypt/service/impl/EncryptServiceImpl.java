/**
 * 
 */
package kr.co.lge.goldstar.mvc.c.encrypt.service.impl;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;

/**
 * @author issuettl
 *
 */
@Service("c.EncryptService")
public class EncryptServiceImpl implements EncryptService {
	
	private static String alg = "AES/CBC/PKCS5Padding";
	
	@Value("${aes.key}")
    private String key; // 32byte

	@Value("${aes.iv}")
    private String iv; // 16byte

	@Override
	public String encryptAES256(String text) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
	}

	@Override
	public String decryptAES256(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
	}

	@Override
	public String maskingName(String name) {
		
		if(!StringUtils.hasText(name)) {
			return null;
		}
		
		switch (name.length()) {
		case 1:
			return "*";
		case 2:
			return name.substring(0, 1) + "*";
		default:
			StringBuilder sb = new StringBuilder(name.substring(0, 1));
			for(int i=1; i < name.length() - 1; i++) {
				sb.append("*");
			}
			sb.append(name.substring(name.length() - 1, name.length()));
			return sb.toString();
		}
	}

	@Override
	public String maskingPhone(String phone) {
		
		if(!StringUtils.hasText(phone)) {
			return null;
		}
		
		switch (phone.length()) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			StringBuilder sb = new StringBuilder();
			for(int i=1; i <= phone.length(); i++) {
				sb.append("*");
			}
			return sb.toString();
		default:
			
			//half / 2 3자리까지만 보이고, 나머지는 모두 * 앞,뒤
			StringBuilder sb1 = new StringBuilder(phone.substring(0, 3));
			for(int i=3; i < phone.length() - 4; i++) {
				sb1.append("*");
			}
			sb1.append(phone.substring(phone.length() - 4, phone.length()));
			return sb1.toString();
		}
	}
}
