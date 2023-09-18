/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.exp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.u.exp.service.ExpService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.CornerType;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.life.LifePartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.mood.MoodPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.refresh.RefreshPartEntity;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.dsl.SignDslRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.LifePartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MoodPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.RefreshPartRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StylePartRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.ExpService")
public class ExpServiceImpl implements ExpService {
	
	@Autowired
	private SignDslRepository signDslRepository;
	
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
	private EncryptService encryptService;
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public DataMap getHistory() {
		
		DataMap result = new DataMap();

		SignEntity signEntity = this.signService.getSignIn();
		
		int memberSn = signEntity.getId().getMemberSn();
		String today = DateUtils.getToday("yyyyMMdd");
		
		Optional<MemberEntity> member = this.memberRepository.findById(memberSn);
		if(member.isEmpty()) {
			return result;
		}
		MemberEntity memberEntity = member.get();
		
		List<SignEntity> signEntities = this.signDslRepository.findByIdMemberSnAndIdCreatedNotOrderByIdCreatedDesc(memberSn, memberEntity.getName(), memberEntity.getPhone(), today);
		if(CollectionUtils.isEmpty(signEntities)) {
			return result;
		}

		result.put("cornerTypes", CornerType.values());
		
		List<DataMap> parts = new ArrayList<>();
		result.put("parts", parts);
		signEntities.forEach(item -> {

			DataMap part = new DataMap();
			parts.add(part);
			
			MindPartEntity mind = this.mindPartRepository.findBySignMemberSnAndSignCreated(item.getId().getMemberSn(), item.getId().getCreated());
			IndivPartEntity indiv = this.indivPartRepository.findBySignMemberSnAndSignCreated(item.getId().getMemberSn(), item.getId().getCreated());
			StylePartEntity style = this.stylePartRepository.findBySignMemberSnAndSignCreated(item.getId().getMemberSn(), item.getId().getCreated());
			MoodPartEntity mood = this.moodPartRepository.findBySignMemberSnAndSignCreated(item.getId().getMemberSn(), item.getId().getCreated());
			RefreshPartEntity refresh = this.refreshPartRepository.findBySignMemberSnAndSignCreated(item.getId().getMemberSn(), item.getId().getCreated());
			List<LifePartEntity> lifes = this.lifePartRepository.findByMemberSnAndDate(item.getId().getMemberSn(), item.getId().getCreated());
			
			try {
				item.setNickDec(this.encryptService.decryptAES256(item.getNick()));
			} catch (Exception e) {
			}
			
			part.put("sign", item);
			part.put("mind", mind);
			part.put("indiv", indiv);
			part.put("style", style);
			part.put("mood", mood);
			part.put("refresh", refresh);
			
			for(LifePartEntity life : lifes) {
				switch (life.getType()) {
				case weekday: //평일
					switch (life.getStatus()) { //상태
					case status2:
					case status4:
						part.put("life", life);
						break;
					default:
						break;
					}
					break;
				case weekend: //주말
					switch (life.getStatus()) { //상태
					case status2:
					case status4:
						part.put("life", life);
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			}
		});
		
		return result;
	}

}
