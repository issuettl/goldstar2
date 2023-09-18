/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.member.service;

import java.util.List;

import org.springframework.data.domain.Page;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
public interface MemberService {

	/**
	 * @param params
	 * @return
	 */
	Page<MemberEntity> getPage(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	DataMap getMember(DataMap params);

	/**
	 * @param params
	 * @return
	 */
	DataMap staffCheck(DataMap params);

	/**
	 * @param dataMap
	 * @return
	 */
	DataMap jammyIssue(DataMap dataMap);

	/**
	 * @return
	 */
	List<MemberEntity> encode();

}
