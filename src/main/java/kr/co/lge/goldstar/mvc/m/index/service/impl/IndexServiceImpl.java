/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.index.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.index.service.IndexService;
import kr.co.lge.goldstar.mvc.m.manager.service.ManagerLogExecution;
import kr.co.lge.goldstar.orm.dynamic.persistence.IndexMapper;
import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.RefreshPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StylePartRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.core.utils.StringUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
@Service("m.IndexService")
public class IndexServiceImpl implements IndexService {

	@Autowired
	private SignRepository signRepository;
	
	@Autowired
	private MindPartRepository mindPartRepository;
	
	@Autowired
	private StylePartRepository stylePartRepository;
	
	@Autowired
	private IndivPartRepository indivPartRepository;
	
	@Autowired
	private MoodPartRepository moodPartRepository;
	
	@Autowired
	private RefreshPartRepository refreshPartRepository;
	
	@Autowired
	private LifePartRepository lifePartRepository;
	
	@Autowired
	private IndexMapper indexMapper;
	
	//@Autowired
	//private IndexDslRepository indexDslRepository;
	
	//@Autowired
	//private MindPartDslRepository mindPartDslRepository;
	
	@Override
	@ManagerLogExecution(process = "getDash(DataMap params)", menu="대쉬보드", button="검색")
	public DataMap getDash(DataMap params) {
		
		DataMap result = new DataMap();
		
		String today = DateUtils.getToday("yyyyMMdd");
		String startDate = params.getAsString("dateStart");
		if(!StringUtils.hasText(startDate) || startDate.length() != 8) {
			startDate = "20221216";
		}
		
		if(Integer.valueOf(startDate) < 20221216) {
			startDate = "20221216";
		}
		
		String endDate = params.getAsString("dateEnd");
		if(!StringUtils.hasText(endDate) || endDate.length() != 8) {
			endDate = today;
		}
		
		//오늘의 고민등록
		long todayWorryCount = this.signRepository.countByIdCreatedAndPursueTypeNotNull(today);
		result.put("todayWorryCount", todayWorryCount);
		
		//누적 고민등록
		long totalWorryCount1 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndWorryTypeNotNull(startDate, endDate);
		long totalWorryCount2 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueTypeNotNull(startDate, endDate);
		result.put("totalWorryCount", totalWorryCount1 + totalWorryCount2);
		
		//오늘 체험인원
		params.put("today", today);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		int todayMemberCount = this.indexMapper.getTodayMemberCount(params);
		result.put("todayMemberCount", todayMemberCount);
		
		//오늘 체험인원
		int totalMemberCount = this.indexMapper.getTotalMemberCount(params);//this.indexDslRepository.getTotalMemberCount(startDate, endDate);
		result.put("totalMemberCount", totalMemberCount);

		//오늘 체험현황
		long mind = this.mindPartRepository.countBySignCreatedAndStaffCheck(today, StaffCheck.present);
		long indiv = this.indivPartRepository.countBySignCreatedAndStaffCheck(today, StaffCheck.present);
		long style = this.stylePartRepository.countBySignCreatedAndStaffCheck(today, StaffCheck.present);
		long mood = this.moodPartRepository.countBySignCreated(today);
		long refresh = this.refreshPartRepository.countBySignCreatedAndStaffCheck(today, StaffCheck.present);
		long life = this.lifePartRepository.countByDateAndStatus(today, LifeStatus.status4);
		result.put("todayPartCount", mind + indiv + style + mood + refresh + life);
		
		//누적 체험현황
		mind = this.mindPartRepository.countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(startDate, endDate, StaffCheck.present);
		indiv = this.indivPartRepository.countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(startDate, endDate, StaffCheck.present);
		style = this.stylePartRepository.countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(startDate, endDate, StaffCheck.present);
		mood = this.moodPartRepository.countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqual(startDate, endDate);
		refresh = this.refreshPartRepository.countBySignCreatedGreaterThanEqualAndSignCreatedLessThanEqualAndStaffCheck(startDate, endDate, StaffCheck.present);
		life = this.lifePartRepository.countByDateGreaterThanEqualAndDateLessThanEqualAndStatus(startDate, endDate, LifeStatus.status4);
		result.put("totalPartCount", mind + indiv + style + mood + refresh + life);

		double total = Double.valueOf(mind + indiv + style + mood + refresh + life);
		
		result.put("mindPercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(mind / total) * 100).intValue());
		result.put("indivPercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(indiv / total) * 100).intValue());
		result.put("stylePercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(style / total) * 100).intValue());
		result.put("moodPercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(mood / total) * 100).intValue());
		result.put("refreshPercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(refresh / total) * 100).intValue());
		result.put("lifePercent", total == 0 ? 0 : Double.valueOf(Double.valueOf(life / total) * 100).intValue());
		
		//체험존 체험 현황
		result.put("mind", mind);
		result.put("indiv", indiv);
		result.put("style", style);
		result.put("mood", mood);
		result.put("refresh", refresh);
		result.put("life", life);

		long type1 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type1);
		long type2 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type2);
		long type3 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type3);
		long type4 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type4);
		long type5 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type5);
		long type6 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type6);
		long type7 = this.signRepository.countByIdCreatedGreaterThanEqualAndIdCreatedLessThanEqualAndPursueType(startDate, endDate, PursueAnswerType.type7);
		
		total = Double.valueOf(type1 + type2 + type3 + type4 + type5 + type6 + type6);
		
		Long[] worriesCount = new Long[] {type1, type2, type3, type4, type5, type6, type7};
		result.put("worriesCount", worriesCount);
		result.put("worries", PursueAnswerType.values());
		
		Integer[] worriesPercent = new Integer[] {
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type1 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type2 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type3 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type4 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type5 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type6 / total) * 100).intValue(), 
				total == 0 ? 0 : Double.valueOf(Double.valueOf(type7 / total) * 100).intValue()};
		result.put("worriesPercent", worriesPercent);
		
		List<String> worryLabels = new ArrayList<>();
		int i = 0;
		for(PursueAnswerType worry : PursueAnswerType.values()) {
			worryLabels.add(worry.getTitle() + " (" + worriesCount[i++] + ")");
		}
		result.put("worryLabels", worryLabels);

		return result;
	}
	
	private long getTodayMemberCount(String today) {
/*
		SubQueryExpression<Tuple> mindPart1 = this.mindPartDslRepository.getTodayMemberCount(today);
		SubQueryExpression<Tuple> mindPart2 = this.mindPartDslRepository.getTodayMemberCount(today);
		SubQueryExpression<Tuple> mindPart3 = this.mindPartDslRepository.getTodayMemberCount(today);
		
		return new SQLQuery<Void>().union(mindPart1, mindPart2, mindPart3).fetchCount();*/
		return 0;
	}

}
