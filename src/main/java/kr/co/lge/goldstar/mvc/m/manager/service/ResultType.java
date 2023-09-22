/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.manager.service;

/**
 * @author issuettl
 *
 */
public enum ResultType {

	/**
	 * 정보 없음
	 */
	NOT_FOUND,

	/**
	 * 정지
	 */
	NOT_USED,

	/**
	 * 권한없음
	 */
	ACCESS_DENIED,

	/**
	 * 비밀번호 틀림
	 */
	INVALID_PASSWORD,

	/**
	 * 아이디 파라메터 오류
	 */
	EMPTY_USERNAME,

	/**
	 * 비밀번호 파라메터 오류
	 */
	EMPTY_PASSWORD,

	/**
	 * 비밀번호 파라메터 오류
	 */
	INVALID_PASSWORD_MAX,

	/**
	 * 비밀번호 파라메터 오류
	 */
	CHANGE_PASSWORD;
}
