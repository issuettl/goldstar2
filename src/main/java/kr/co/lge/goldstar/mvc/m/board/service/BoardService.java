package kr.co.lge.goldstar.mvc.m.board.service;

import org.springframework.data.domain.Page;

import kr.co.lge.goldstar.orm.jpa.entity.board.BbsEntity;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 11. 15
 *
 */
public interface BoardService {
    
    /**
     * @param bbsSn 
     * @param sn
     * @return
     */
     BoardEntity getBoardEntity(int bbsSn, int sn);

    /**
     * @param bbsSn 
     * @param params
     * @return
     */
    Page<BoardEntity> getPage(int bbsSn, DataMap params);
    
    /**
     * @param bbsSn
     * @return
     */
    BbsEntity getBbsEntity(int bbsSn);

	/**
	 * @param boardEntity
	 * @return
	 */
	DataMap saveAction(BoardEntity boardEntity);

	/**
	 * @param boardEntity
	 * @return
	 */
	DataMap updateAction(BoardEntity boardEntity);

	/**
	 * @param boardEntity
	 * @return
	 */
	DataMap removeAction(BoardEntity boardEntity);

	/**
	 * @param params
	 * @return
	 */
	DataMap orderAction(DataMap params);

}
