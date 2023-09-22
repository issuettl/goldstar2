/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.style.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.lge.goldstar.mvc.m.style.service.StyleService;
import kr.co.lge.goldstar.orm.jpa.entity.style.StylePartEntity;
import kr.co.lge.goldstar.orm.jpa.repository.spring.StylePartRepository;

/**
 * @author NEOFLOW
 *
 */
@Service("m.StyleService")
public class StyleServiceImpl implements StyleService {
	
	@Autowired
	private StylePartRepository stylePartRepository;

	@Override
	public StylePartEntity getStylePart(int memberSn, String create) {
		return this.stylePartRepository.findBySignMemberSnAndSignCreated(memberSn, create);
	}

	@Override
	public void save(StylePartEntity stylePart) {
		this.stylePartRepository.save(stylePart);
	}

}