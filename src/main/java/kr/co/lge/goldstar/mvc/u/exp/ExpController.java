/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.exp;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.lge.goldstar.mvc.m.refresh.service.RefreshService;
import kr.co.lge.goldstar.mvc.u.exp.service.ExpService;
import kr.co.lge.goldstar.mvc.u.indiv.service.IndivService;
import kr.co.lge.goldstar.mvc.u.life.service.LifeService;
import kr.co.lge.goldstar.mvc.u.mind.service.MindService;
import kr.co.lge.goldstar.mvc.u.mood.service.MoodService;
import kr.co.lge.goldstar.mvc.u.product.service.ProductService;
import kr.co.lge.goldstar.mvc.u.pursue.service.PursueService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.mvc.u.style.service.StyleService;
import kr.co.lge.goldstar.mvc.u.survey.service.SurveyService;
import kr.co.lge.goldstar.orm.jpa.entity.AgeType;
import kr.co.lge.goldstar.orm.jpa.entity.GenderType;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.pursue.PursueEntity;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyEntity;
import kr.co.lge.goldstar.orm.jpa.entity.survey.SurveyPartEntity;
import kr.co.rebel9.core.utils.DateUtils;

/**
 * @author issuettl
 *
 */
@Controller("u.ExpController")
@RequestMapping(value = "u/exp")
public class ExpController {
	
	@Autowired
	private SignService signService;
	
	@Resource(name = "u.MindService")
	private MindService mindService;

	@Resource(name = "u.StyleService")
	private StyleService styleService;

	@Resource(name = "u.IndivService")
	private IndivService indivService;

	@Resource(name = "u.MoodService")
	private MoodService moodService;

	@Resource(name = "m.RefreshService")
	private RefreshService refreshService;

	@Resource(name = "u.LifeService")
	private LifeService lifeService;

	@Resource(name = "u.PursueService")
	private PursueService pursueService;

	@Resource(name = "u.SurveyService")
	private SurveyService surveyService;

	@Resource(name = "u.ProductService")
	private ProductService productService;
	
	@Autowired
	private ExpService expService;
	
	@Value("${system.domain}")
	private String systemDomain;
	
	@RequestMapping(value = "qr.do")
	public ModelAndView qr(HttpServletRequest request, ModelMap map) {
		
		SignEntity signEntity = this.signService.getSignIn();
		map.put("content", systemDomain + "/m/sign/qr/" + signEntity.getId().getMemberSn() + "/" + signEntity.getId().getCreated() + ".do");
		
		return new ModelAndView("QrCodeView");
	}
	
	@RequestMapping(value = "qr/big.do")
	public ModelAndView qrBig(HttpServletRequest request, ModelMap map) {
		
		SignEntity signEntity = this.signService.getSignIn();
		map.put("content", systemDomain + "/m/sign/qr/" + signEntity.getId().getMemberSn() + "/" + signEntity.getId().getCreated() + ".do");
		
		return new ModelAndView("QrCodeBigView");
	}
	
	@RequestMapping(value = "qr/page.do")
	public String qrPage(ModelMap map) {
		
		//로그인정보
		SignEntity signEntity = this.signService.getSignIn();
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		
		map.put("sign", signEntity);
		
		return "u/exp/qr";
	}
	
	@RequestMapping(value = "index.do")
	public String index(ModelMap model) {
		
		boolean isMemberIn = this.signService.existMemberIn();
		model.put("isMemberIn", isMemberIn);
		
		boolean isSignIn = this.signService.existSignIn();
		model.put("isSignIn", isSignIn);
		
		return "u/exp/index";
	}
	
	@RequestMapping(value = "worry.do")
	public String worry(ModelMap model) {
		
		//로그인정보
		SignEntity signEntity = this.signService.getSignEntity();
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}

		if(!ObjectUtils.isEmpty(signEntity.getPursueType())) {
			return "redirect:"  + systemDomain +  "/u/exp/corner.do";
		}
		
		List<PursueEntity> questions = this.pursueService.getQuestions();
		model.put("questions", questions);
		
		return "u/exp/worry";
	}
	
	@RequestMapping(value = "gender.do")
	public String gender(ModelMap map) {
		map.put("genders", GenderType.values());
		return "u/exp/gender";
	}
	
	@RequestMapping(value = "age.do")
	public String age(ModelMap map) {
		map.put("ages", AgeType.values());
		return "u/exp/age";
	}
	
	@RequestMapping(value = "corner.do")
	public String corner(ModelMap map) {
		
		//로그인정보
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}

		//로그인정보
		SignEntity signEntity = this.signService.getSignEntity();
		if(ObjectUtils.isEmpty(signEntity.getPursueType())) {
			return "redirect:"  + systemDomain +  "/u/exp/worry.do";
		}
		
		MindPartEntity mind = this.mindService.getMindPart();
		StylePartEntity style = this.styleService.getStylePart();
		IndivPartEntity indiv = this.indivService.getIndivPart();
		MoodPartEntity mood = this.moodService.getMoodPart();
		RefreshPartEntity refresh = this.refreshService.getRefreshPart();
		LifePartEntity life = this.lifeService.getLifePart();
		SurveyPartEntity survey = this.surveyService.getSurveyPart();
		map.put("mind", mind);
		map.put("style", style);
		map.put("indiv", indiv);
		map.put("mood", mood);
		map.put("refresh", refresh);
		map.put("life", life);
		map.put("survey", survey);
		
		boolean isPart = false;
		isPart = isPart || (!ObjectUtils.isEmpty(mind) && StaffCheck.present.equals(mind.getStaffCheck()));
		isPart = isPart || (!ObjectUtils.isEmpty(style) && StaffCheck.present.equals(style.getStaffCheck()));
		isPart = isPart || (!ObjectUtils.isEmpty(indiv) && StaffCheck.present.equals(indiv.getStaffCheck()));
		isPart = isPart || (!ObjectUtils.isEmpty(mood) && StaffCheck.present.equals(mood.getStaffCheck()));
		isPart = isPart || (!ObjectUtils.isEmpty(refresh) && StaffCheck.present.equals(refresh.getStaffCheck()));
		isPart = isPart || (!ObjectUtils.isEmpty(life) && LifeStatus.status4.equals(life.getStatus()));
		map.put("isPart", isPart);
		
		map.put("products", this.productService.products(signEntity.getPursueType()));
		
		List<SurveyEntity> questions = this.surveyService.getQuestions();
		map.put("questions", questions);
		
		return "u/exp/corner";
	}
	
	@RequestMapping(value = "cert.do")
	public String cert(ModelMap map) {
		
		//로그인정보
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		
		MindPartEntity mind = this.mindService.getMindPart();
		StylePartEntity style = this.styleService.getStylePart();
		IndivPartEntity indiv = this.indivService.getIndivPart();
		MoodPartEntity mood = this.moodService.getMoodPart();
		RefreshPartEntity refresh = this.refreshService.getRefreshPart();
		LifePartEntity life = this.lifeService.getLifePart();
		
		int level = 0;;
		if(!ObjectUtils.isEmpty(mind) && StaffCheck.present.equals(mind.getStaffCheck())) {
			map.put("mind", mind);
			level++;
		};
		if(!ObjectUtils.isEmpty(style) && StaffCheck.present.equals(style.getStaffCheck())) {
			map.put("style", style);
			level++;
		};
		if(!ObjectUtils.isEmpty(indiv) && StaffCheck.present.equals(indiv.getStaffCheck())) {
			map.put("indiv", indiv);
			level++;
		}
		if(!ObjectUtils.isEmpty(mood) && StaffCheck.present.equals(mood.getStaffCheck())) {
			map.put("mood", mood);
			level++;
		}
		if(!ObjectUtils.isEmpty(refresh) && StaffCheck.present.equals(refresh.getStaffCheck())) {
			map.put("refresh", refresh);
			level++;
		}
		if(!ObjectUtils.isEmpty(life) && LifeStatus.status4.equals(life.getStatus())) {
			map.put("life", life);
			level++;
		}
		if(level < 3) {
			level = 1;
		}else if(level < 6) {
			level = 2;
		}
		else {
			level = 3;
		}
		map.put("level", level);
		
		return "u/exp/cert";
	}
	
	@RequestMapping(value = "gift.do")
	public String gift(ModelMap map) {
		
		//로그인정보
		SignEntity signEntity = this.signService.getSignIn();
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		
		MemberEntity memberEntity = this.signService.getMemberEntity(signEntity.getId().getMemberSn());
		map.put("member", memberEntity);
		
		MindPartEntity mind = this.mindService.getMindPart();
		StylePartEntity style = this.styleService.getStylePart();
		IndivPartEntity indiv = this.indivService.getIndivPart();
		map.put("mind", mind);
		map.put("style", style);
		map.put("indiv", indiv);
		
		return "u/exp/gift";
	}
	
	@RequestMapping(value = "history.do")
	public String history(ModelMap map) {
		
		//로그인정보
		MemberEntity member = this.signService.getMemberIn();
		
		//성별
		if(ObjectUtils.isEmpty(member.getGender())) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedGender()) && !member.getUpdatedGender().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/gender.do";
		}
		
		//나이
		if(ObjectUtils.isEmpty(member.getAge())) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		//올해저장된 데이터가 아니면 다시 진행
		else if(StringUtils.isNotEmpty(member.getUpdatedAge()) && !member.getUpdatedAge().substring(0, 4).equals(DateUtils.getToday("yyyy"))) {
			return "redirect:"  + systemDomain +  "/u/exp/age.do";
		}
		
		map.putAll(this.expService.getHistory());
		
		return "u/exp/history";
	}
}
