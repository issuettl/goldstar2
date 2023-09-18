/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl;

import java.util.List;

import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 14
 *
 */
public interface SignDslRepository {

	/**
S	 * @param memberSn
	 * @param memberName
	 * @param memberPhone
	 * @param created
	 * @return
	 */
	List<SignEntity> findByIdMemberSnAndIdCreatedNotOrderByIdCreatedDesc(int memberSn, String memberName, String memberPhone, String created);
}
