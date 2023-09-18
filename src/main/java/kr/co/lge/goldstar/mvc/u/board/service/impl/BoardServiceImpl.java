package kr.co.lge.goldstar.mvc.u.board.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.u.board.service.BoardService;
import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
import kr.co.lge.goldstar.orm.jpa.entity.board.BbsEntity;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.BoardDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.BbsRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.BoardRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
@Service("u.BoardService")
public class BoardServiceImpl implements BoardService {
    
    @Resource(name = "BoardRepository")
    private BoardRepository boardRepository;

    @Resource(name = "BoardDslRepository")
    private BoardDslRepository boardDslRepository;
    
    @Resource(name = "BbsRepository")
    private BbsRepository bbsRepository;

    @Override
    public BoardEntity getBoardEntity(int bbsSn, int sn) {
        
    	BoardEntity entity = this.boardRepository.findByBbsSnAndSnAndDeleted(bbsSn, sn, YesOrNo.N);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        entity.setRead(entity.getRead() + 1);
        this.boardRepository.save(entity);
        
        if(StringUtils.hasLength(entity.getContents())) {
        	entity.setContents(kr.co.rebel9.core.utils.StringUtils.nl2br(entity.getContents()));
        }

        String today = DateUtils.getToday("yyyyMMdd");
        if(DateUtils.getDiffDayCount(entity.getCreated().substring(0, 8), today) < 7) {
        	entity.setNewIcon(true);
    	}

        return entity;
    }

    @Override
    public BoardEntity getBoardPrevEntity(int bbsSn, int sn) {
        
    	BoardEntity entity = this.boardRepository.findFirstByBbsSnAndDeletedAndSnGreaterThanOrderBySnAsc(bbsSn, YesOrNo.N, sn);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        return entity;
    }

    @Override
    public BoardEntity getBoardNextEntity(int bbsSn, int sn) {
        
    	BoardEntity entity = this.boardRepository.findFirstByBbsSnAndDeletedAndSnLessThanOrderBySnDesc(bbsSn, YesOrNo.N, sn);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        return entity;
    }

    @Override
    public Page<BoardEntity> getPage(int bbsSn, DataMap params) {
        
        Page<BoardEntity> page = this.boardDslRepository.findPageByHome(
                bbsSn, params, PageRequestSupport.getPageRequest(params));
        
        if(ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.getContent())){
            return page;
        }
        
        //if 콘텐츠 변경
        String today = DateUtils.getToday("yyyyMMdd");
        page.getContent().forEach(item -> {
        	if(DateUtils.getDiffDayCount(item.getCreated().substring(0, 8), today) < 7) {
        		item.setNewIcon(true);
        	}
        	
        	if(StringUtils.hasLength(item.getContents())) {
        		item.setContents(kr.co.rebel9.core.utils.StringUtils.nl2br(item.getContents()));
            }
        });
        
        return page;
    }

    @Override
    public BbsEntity getBbsEntity(int bbsSn) {
        return this.bbsRepository.findBySnAndUsed(bbsSn, YesOrNo.Y);
    }
}
