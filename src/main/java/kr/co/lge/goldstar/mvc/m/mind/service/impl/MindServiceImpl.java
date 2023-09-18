/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.mind.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.mind.service.MindService;
import kr.co.lge.goldstar.orm.jpa.entity.mind.MindPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MindPartRepository;

/**
 * @author NEOFLOW
 *
 */
@Service("m.MindService")
public class MindServiceImpl implements MindService {
	
	@Autowired
	private MindPartRepository mindPartRepository;

	@Override
	public MindPartEntity getMindPart(int memberSn, String create) {
		return this.mindPartRepository.findBySignMemberSnAndSignCreated(memberSn, create);
	}

	@Override
	public void save(MindPartEntity mindPart) {
		this.mindPartRepository.save(mindPart);
	}

}
