/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.indiv.service;

import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;

/**
 * @author issuettl
 *
 */
public interface IndivService {

	/**
	 * @param string 
	 * @param i 
	 * @return
	 */
	IndivPartEntity getIndivPart(int memberSn, String create);

	/**
	 * @param indivPart
	 */
	void save(IndivPartEntity indivPart);

}
