/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.style.service;

import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;

/**
 * @author issuettl
 *
 */
public interface StyleService {

	/**
	 * @param memberSn
	 * @param created
	 * @return
	 */
	StylePartEntity getStylePart(int memberSn, String created);

	/**
	 * @param stylePart
	 */
	void save(StylePartEntity stylePart);

}
