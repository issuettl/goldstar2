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

import kr.co.lge.goldstar.orm.jpa.entity.member.ManagerEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.ManagerRepository;
import kr.co.rebel9.core.utils.DateUtils;

/**
 * @author issuettl
 *
 */
@Component
public class ManagerScheduler {

	@Autowired
	private ManagerRepository managerRepository;
	
	@Scheduled(cron = "0 0 01 * * ?")
	public void managerLocking() {
		
		String target = DateUtils.getToday("yyyyMMdd");
		target = DateUtils.getAddDate(target, Calendar.MONTH, -3, "yyyyMMdd");
		//target = DateUtils.getAddDate(target, Calendar.DATE, -1, "yyyyMMdd");
		
		List<ManagerEntity> managers = this.managerRepository.findAllByConnectedLessThanEqual(target + "000000");
		
		if(CollectionUtils.isEmpty(managers)) {
			return;
		}
		
		String todate = DateUtils.getToday("yyyyMMddHHmmss");
		managers.forEach(item -> {
			item.setLocked(todate);
		});
		
		this.managerRepository.saveAll(managers);
	}
}
