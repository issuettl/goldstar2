/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.QMemberEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.MemberDslRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("MemberDslRepository")
public class MemberDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements MemberDslRepository {

	@Autowired
	private EncryptService encryptService;
	
    /**
     * @param domainClass
     */
    public MemberDslRepositoryImpl() {
        super(MemberEntity.class);
    }

    @Override
    public Page<MemberEntity> findPageBySearch(DataMap params, Pageable pageable) {
        
        QMemberEntity entity = QMemberEntity.memberEntity;
        JPQLQuery<MemberEntity> query = from(entity);
        
        //제목
        String name = params.getAsString("name");
        if(StringUtils.hasText(name)) {
            try {
				query.where(entity.name.eq(this.encryptService.encryptAES256(name)).or(entity.name.eq(name)));
			} catch (Exception e) {
			}
        }
        //전화번호
        String phone = params.getAsString("phone");
        if(StringUtils.hasText(phone)) {
            try {
				query.where(entity.phone.eq(this.encryptService.encryptAES256(phone)).or(entity.phone.eq(phone)));
			} catch (Exception e) {
			}
        }
        
        //시작일
        String dateStart = params.getAsString("dateStart");
        if(StringUtils.hasText(dateStart)){
            query.where(entity.created.goe(dateStart + "000000"));
        }
        
        //종료일
        String dateEnd = params.getAsString("dateEnd");
        if(StringUtils.hasText(dateEnd)){
            query.where(entity.created.loe(dateEnd + "235959"));
        }
        
        getQuerydsl().applyPagination(pageable, query);
        getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

}
