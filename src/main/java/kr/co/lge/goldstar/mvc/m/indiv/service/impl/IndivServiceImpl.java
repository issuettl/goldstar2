/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.indiv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.indiv.service.IndivService;
import kr.co.lge.goldstar.orm.jpa.entity.indiv.IndivPartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.IndivPartRepository;

/**
 * @author NEOFLOW
 *
 */
@Service("m.IndivService")
public class IndivServiceImpl implements IndivService {
	
	@Autowired
	private IndivPartRepository indivPartRepository;

	@Override
	public IndivPartEntity getIndivPart(int memberSn, String create) {
		return this.indivPartRepository.findBySignMemberSnAndSignCreated(memberSn, create);
	}

	@Override
	public void save(IndivPartEntity indivPart) {
		this.indivPartRepository.save(indivPart);
	}

}
