/**
 * 
 */
package kr.co.lge.goldstar.core.scheduler;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.rebel9.core.utils.DateUtils;

/**
 * @author issuettl
 *
 */
@Component
public class MemberScheduler {

	@Autowired
	private MemberRepository memberRepository;
	
	@Scheduled(cron = "0 35 01 * * ?")
	public void MemberPersonal() {
		
		System.out.println("----MemberPersonal---- start");
		
		String target = DateUtils.getToday("yyyyMMdd");
		target = DateUtils.getAddDate(target, Calendar.MONTH, -3, "yyyyMMdd");
		//target = DateUtils.getAddDate(target, Calendar.DATE, -1, "yyyyMMdd");
		
		List<MemberEntity> members = this.memberRepository.findAllByUpdatedLessThanEqualAndUpdatedPersonalIsNull(target + "000000");
		
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
