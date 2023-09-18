/**
 * 
 */
package kr.co.lge.goldstar.mvc.c.encrypt.service;

/**
 * @author issuettl
 *
 */
public interface EncryptService {

	/**
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public String encryptAES256(String text) throws Exception;

	/**
	 * @param cipherText
	 * @return
	 * @throws Exception
	 */
	public String decryptAES256(String cipherText) throws Exception;

	/**
	 * @param name
	 * @return
	 */
	public String maskingName(String name);

	/**
	 * @param phone
	 * @return
	 */
	public String maskingPhone(String phone);
}
