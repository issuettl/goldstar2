package kr.co.lge.goldstar.mvc.m.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import kr.co.lge.goldstar.core.pagination.PageRequestSupport;
import kr.co.lge.goldstar.mvc.m.board.service.BoardService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
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
@Service("m.BoardService")
public class BoardServiceImpl implements BoardService {
    
    @Resource(name = "BoardRepository")
    private BoardRepository boardRepository;

    @Resource(name = "BoardDslRepository")
    private BoardDslRepository boardDslRepository;
    
    @Resource(name = "BbsRepository")
    private BbsRepository bbsRepository;

    @Override
	@ManagerLogExecution(process = "getBoardEntity(int bbsSn, int sn)", menu="게시판 관리", button="상세")
    public BoardEntity getBoardEntity(int bbsSn, int sn) {
        
    	BoardEntity entity = this.boardRepository.findByBbsSnAndSnAndDeleted(bbsSn, sn, YesOrNo.N);
        
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        
        if(StringUtils.hasLength(entity.getContents())) {
        	entity.setContentsBr(kr.co.rebel9.core.utils.StringUtils.nl2br(entity.getContents()));
        }

        return entity;
    }

    @Override
	@ManagerLogExecution(process = "getPage(int bbsSn, DataMap params)", menu="게시판 관리", button="검색")
    public Page<BoardEntity> getPage(int bbsSn, DataMap params) {
        
        Page<BoardEntity> page = this.boardDslRepository.findPageBySearch(
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

	@Override
	@ManagerLogExecution(process = "saveAction(BoardEntity params)", menu="게시판 관리", button="등록")
	public DataMap saveAction(BoardEntity params) {
		
		params.setBbs(getBbsEntity(params.getBbsSn()));
		params.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
		params.setDeleted(YesOrNo.N);
		
		this.boardRepository.save(params);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "updateAction(BoardEntity params)", menu="게시판 관리", button="수정")
	public DataMap updateAction(BoardEntity params) {
		
		BoardEntity boardEntity = getBoardEntity(params.getBbsSn(), params.getSn());
		if(ObjectUtils.isEmpty(boardEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		boardEntity.setSubject(params.getSubject());
		boardEntity.setContents(params.getContents());
		
		this.boardRepository.save(boardEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "removeAction(BoardEntity params)", menu="게시판 관리", button="삭제")
	public DataMap removeAction(BoardEntity params) {
		
		BoardEntity boardEntity = getBoardEntity(params.getBbsSn(), params.getSn());
		if(ObjectUtils.isEmpty(boardEntity)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		boardEntity.setDeleted(YesOrNo.Y);
		
		this.boardRepository.save(boardEntity);
		
		return new DataMap(true);
	}

	@Override
	@ManagerLogExecution(process = "orderAction(BoardEntity params)", menu="게시판 관리", button="정렬")
	public DataMap orderAction(DataMap params) {
		
		List<Integer> snList = params.getListAsInteger("snList[]");
		
		List<BoardEntity> boardEntities = this.boardRepository.findBySnIn(snList);
		
		if(CollectionUtils.isEmpty(boardEntities)) {
			DataMap result = new DataMap(false);
			result.put("reason", "데이터를 찾을 수 없습니다.");
			return result;
		}
		
		Map<Integer, BoardEntity> boardMap = new HashMap<>();
		boardEntities.forEach(item -> {
			boardMap.put(item.getSn(), item);
		});
		
		int order = 1;
		for(Integer sn : snList) {
			boardMap.get(sn).setOrder(order++);
		}
		
		this.boardRepository.saveAll(boardEntities);
		
		return new DataMap(true);
	}
}
