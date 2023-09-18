/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.lge.goldstar.mvc.m.board.service.BoardService;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.rest.result.ResultBuilder;
import kr.co.rebel9.web.rest.result.ResultConst;

/**
 * @author issuettl
 *
 */
@RestController("m.BoardRestController")
@RequestMapping(value = "m/board")
public class BoardRestController {

	@Autowired
	private BoardService boardService;
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("save/action.do")
	public DataMap saveAction(@RequestBody BoardEntity boardEntity) {
        try {
            return this.boardService.saveAction(boardEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("update.do")
	public DataMap update(@RequestBody BoardEntity boardEntity) {
        try {
        	DataMap result = new DataMap(true);
        	result.put("board", this.boardService.getBoardEntity(boardEntity.getBbsSn(), boardEntity.getSn()));
            return result;
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("update/action.do")
	public DataMap updateAction(@RequestBody BoardEntity boardEntity) {
        try {
            return this.boardService.updateAction(boardEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("remove/action.do")
	public DataMap remove(@RequestBody BoardEntity boardEntity) {
        try {
            return this.boardService.removeAction(boardEntity);
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
	
	/**
	 * @param param
	 * @param session
	 * @return 로그인 결과
	 */
	@PostMapping("order/action.do")
	public DataMap order(@RequestParam MultiValueMap<String, Object> paramMap) {
        try {
            return this.boardService.orderAction(new DataMap(paramMap));
        }catch(Exception e) {
			DataMap result = new DataMap(false);
			result.put(ResultConst.META, ResultBuilder.failure(e));
			return result;
		}
	}
}
