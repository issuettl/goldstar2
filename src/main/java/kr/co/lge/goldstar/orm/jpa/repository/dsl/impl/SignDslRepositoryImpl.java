/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.orm.jpa.entity.member.QSignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.SignDslRepository;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("SignDslRepository")
public class SignDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements SignDslRepository {

    /**
     * @param domainClass
     */
    public SignDslRepositoryImpl() {
        super(SignEntity.class);
    }

    @Override
    public List<SignEntity> findByIdMemberSnAndIdCreatedNotOrderByIdCreatedDesc(int memberSn, String memberName, String memberPhone, String created) {
        
        QSignEntity entity = QSignEntity.signEntity;
        JPQLQuery<SignEntity> query = from(entity);
        
        query.where(
        		entity.member.sn.eq(memberSn).or(
        				entity.member.name.eq(memberName).and(entity.member.phone.eq(memberPhone))));
        
        query.where(entity.id.created.ne(created));

        getQuerydsl().applySorting(Sort.by(Direction.DESC, "id.created"), query);
        
        return query.fetch();
    }

}
