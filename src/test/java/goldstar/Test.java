/**
 * 
 */
package goldstar;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author NEOFLOW
 *
 */
public class Test {

	private static String alg = "AES/CBC/PKCS5Padding";
	
    private static String key = "rmatjdwjsvktkqkrwjdrudelzktkgk24"; // 32byte

    private static String iv = "qkrwjdruddlqslek"; //
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String state = "9qjOqh8jl3GFO/v4WQY IQ2MMauf6xFe2CxXNtRK 38=";
		String state2 = "9qjOqh8jl3GFO/v4WQY+IQ2MMauf6xFe2CxXNtRK+38";

		try {
			state = state.replaceAll(" ", "+");
			System.out.println(decryptAES256(state));
			System.out.println(decryptAES256(state2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String decryptAES256(String cipherText) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, "UTF-8");
	}
}
