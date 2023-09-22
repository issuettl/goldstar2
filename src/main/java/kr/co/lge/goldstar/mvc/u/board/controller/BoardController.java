package kr.co.lge.goldstar.mvc.u.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.lge.goldstar.core.pagination.Paging;
import kr.co.lge.goldstar.mvc.u.board.service.BoardService;
import kr.co.lge.goldstar.orm.jpa.entity.board.BbsEntity;
import kr.co.lge.goldstar.orm.jpa.entity.board.BoardEntity;
import kr.co.rebel9.web.data.DataMap;

/**
 * TODO desc
 * @author base@rebel9.co.kr JongHan Park
 * @since Jun 27, 2019
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *  수정일     수정자     수정내용
 *  -------     --------    ---------------------------
 *  Jun 27, 2019        박종한     최초 생성
 */
@Controller("u.BoardController")
@RequestMapping(value = "u/board")
public class BoardController {
    
    @Autowired
    private BoardService boardService;
	
	@Value("${system.domain}")
	private String systemDomain;
    
    /**
     * @param bbsSn 
     * @param paramMap
     * @param model
     * @param session 
     * @return view
     */
    @RequestMapping(value = "{bbsSn}/list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@PathVariable int bbsSn, @RequestParam MultiValueMap<String, Object> paramMap, ModelMap model, HttpSession session) {
        
        DataMap params = new DataMap(paramMap);
        params.put("size", params.getAsInt("size", 10));
        
        BbsEntity bbs = this.boardService.getBbsEntity(bbsSn);
        if(ObjectUtils.isEmpty(bbs)) {
            return "redirect:" + systemDomain + "/u/board/1/list.do";
        }

        Page<BoardEntity> page = this.boardService.getPage(bbsSn, params);
        model.put("bbs", bbs);
        model.put("page", page);
        model.put("paging", new Paging(page));
        
        model.put("params", params);
        
        return "u/board/" + bbsSn + "/list";
    }
    
    /**
     * @param bbsSn 
     * @param sn 
     * @param paramMap
     * @param model
     * @param session 
     * @return view
     */
    @RequestMapping(value = "{bbsSn}/view/{sn}.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String view(@PathVariable int bbsSn, @PathVariable int sn, @RequestParam MultiValueMap<String, Object> paramMap, ModelMap model, HttpSession session) {
        
        BoardEntity entity = this.boardService.getBoardEntity(bbsSn, sn);
        
        if(ObjectUtils.isEmpty(entity)) {
            return "redirect:/u/board/index.do";
        }
        model.put("board", entity);
        model.put("prev", this.boardService.getBoardPrevEntity(bbsSn, sn));
        model.put("next", this.boardService.getBoardNextEntity(bbsSn, sn));

        return "u/board/" + bbsSn + "/view";
    }
}
