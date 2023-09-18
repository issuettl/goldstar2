package kr.co.lge.goldstar.mvc.u.board.service;

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
  	 * @param sn
  	 * @return
  	 */
      BoardEntity getBoardPrevEntity(int bbsSn, int sn);

   	/**
   	 * @param bbsSn
   	 * @param sn
   	 * @return
   	 */
       BoardEntity getBoardNextEntity(int bbsSn, int sn);

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

}
