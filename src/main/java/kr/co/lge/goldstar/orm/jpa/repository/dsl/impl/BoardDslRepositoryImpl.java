/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.repository.dsl.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPQLQuery;

import kr.co.lge.goldstar.core.repository.GoldStarQuerydslRepositorySupport;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;
import kr.co.lge.goldstar.orm.jpa.entity.board.QBoardEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.BoardDslRepository;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 27
 *
 */

@Repository("BoardDslRepository")
public class BoardDslRepositoryImpl extends GoldStarQuerydslRepositorySupport implements BoardDslRepository {

    /**
     * @param domainClass
     */
    public BoardDslRepositoryImpl() {
        super(BoardEntity.class);
    }

    @Override
    public Page<BoardEntity> findPageBySearch(int bbsSn, DataMap params, Pageable pageable) {
        
        QBoardEntity entity = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> query = from(entity);
        
        //게시판 번호
        query.where(entity.bbsSn.eq(bbsSn));

        //삭제여부
        query.where(entity.deleted.eq(YesOrNo.N));
        
        getQuerydsl().applyPagination(pageable, query);
        
        if(bbsSn == 1) {
            getQuerydsl().applySorting(Sort.by(Direction.ASC, "order"), query);
        }
        else {
            getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        }
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

    @Override
    public Page<BoardEntity> findPageByHome(int bbsSn, DataMap params, Pageable pageable) {
        
        QBoardEntity entity = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> query = from(entity);
        
        //게시판 번호
        query.where(entity.bbsSn.eq(bbsSn));

        //삭제여부
        query.where(entity.deleted.eq(YesOrNo.N));
        
        getQuerydsl().applyPagination(pageable, query);
        
        if(bbsSn == 1) {
            getQuerydsl().applySorting(Sort.by(Direction.ASC, "order"), query);
        }
        else {
            getQuerydsl().applySorting(Sort.by(Direction.DESC, "sn"), query);
        }
        
        return new PageImpl<>(query.fetch(), pageable, query.fetchCount());
    }

}
