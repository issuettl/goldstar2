/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.member.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.u.member.service.MemberService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author issuettl
 *
 */
@Service("u.MemberService")
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private SignService signService;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public DataMap saveGender(MemberEntity memberEntity) {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		Optional<MemberEntity> memberSaved = this.memberRepository.findById(signEntity.getMember().getSn());
		if(memberSaved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원 정보를 찾을 수 없습니다.");
			return result;
		}
		
		MemberEntity saved = memberSaved.get();
		saved.setGender(memberEntity.getGender());
		saved.setUpdatedGender(DateUtils.getToday("yyyyMMddHHmmss"));
		
		this.memberRepository.save(saved);
		
		//로그인초기화
		this.signService.signInMember(saved);
		
		return new DataMap(true);
	}

	@Override
	public DataMap saveAge(MemberEntity memberEntity) {
		
		SignEntity signEntity = this.signService.getSignIn();
		
		Optional<MemberEntity> memberSaved = this.memberRepository.findById(signEntity.getMember().getSn());
		if(memberSaved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "회원 정보를 찾을 수 없습니다.");
			return result;
		}
		
		MemberEntity saved = memberSaved.get();
		saved.setAge(memberEntity.getAge());
		saved.setUpdatedAge(DateUtils.getToday("yyyyMMddHHmmss"));
		
		this.memberRepository.save(saved);
		
		//로그인초기화
		this.signService.signInMember(saved);
		
		return new DataMap(true);
	}

	@Override
	public void personal(DataMap params) {
		
		System.out.println("----MemberPersonal---- start");
		
		String tFormat = params.getAsString("ymd") == null ? "yyyyMMdd" : params.getAsString("ymd");
		String target = DateUtils.getToday(tFormat);
		target = DateUtils.getAddDate(target, Calendar.MONTH, -3, tFormat);
		
		System.out.println("----target1---- " + target);
		
		List<MemberEntity> members = this.memberRepository.findAllByUpdatedLessThanEqualAndUpdatedPersonalIsNull(target + "000000");

		System.out.println("----target2---- " + target + "000000");
		System.out.println("----members----" + members.size());
		
		if(CollectionUtils.isEmpty(members)) {
			return;
		}
		
		String todate = DateUtils.getToday("yyyyMMddHHmmss");
		members.forEach(item -> {
			item.setName(null);
			item.setPhone(null);
			item.setUpdatedPersonal(todate);
		});
		
		this.memberRepository.saveAll(members);
		System.out.println("----MemberPersonal---- end");
	}
}
