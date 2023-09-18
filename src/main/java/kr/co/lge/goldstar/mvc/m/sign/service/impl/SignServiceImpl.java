/**
 * 
 */
package kr.co.lge.goldstar.mvc.m.sign.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.m.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.web.data.DataMap;

/**
 * @author NEOFLOW
 *
 */
@Service("m.SignService")
public class SignServiceImpl implements SignService {
	
	@Autowired
	private SignRepository signRepository;
	
	@Autowired
	private EncryptService encryptService;

	@Override
	public DataMap getSign(int memberSn, String created) {

		String today = DateUtils.getToday("yyyyMMdd");
		
		if(!today.equals(created)) {
			DataMap result = new DataMap(false);
			result.put("reason", "유효한 날짜가 아닙니다.");
			result.put("code", 403);
			return result;
		}
		
		SignId signId = new SignId();
		signId.setMemberSn(memberSn);
		signId.setCreated(created);

		Optional<SignEntity> sign = this.signRepository.findById(signId);
		
		if(sign.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "찾을수 없습니다.");
			result.put("code", 404);
			return result;
		}
		
		DataMap result = new DataMap(true);

		SignEntity signEntity = sign.get();
		try {
			//QR 리딩 for APP
			signEntity.setNick(this.encryptService.decryptAES256(signEntity.getNick()));
		} catch (Exception e) {
		}
		
		result.put("sign", signEntity);
		
		if(ObjectUtils.isEmpty(signEntity.getMember())) {
			signEntity.getMember().getJammy();
		}
		
		return result;
	}

	@Override
	public DataMap getSigns(int count) {

		Pageable topCount = PageRequest.of(0, count, Direction.DESC, "added");
		List<SignEntity> signList = this.signRepository.findBy(topCount);
		
		if(!CollectionUtils.isEmpty(signList)) {
			signList.forEach(item -> {
				try {
					item.setNick(this.encryptService.decryptAES256(item.getNick()));
				} catch (Exception e) {
				}
			});
		}
		
		DataMap result = new DataMap(true);
		result.put("signs", signList);
		
		return result;
	}

	@Override
	public SignEntity getSignEntity(int sn, String created) {
		
		SignId signId = new SignId(sn, created);
		Optional<SignEntity> sign = this.signRepository.findById(signId);

		if(sign.isEmpty()) {
			return null;
		}
		
		SignEntity signEntity = sign.get();
		try {
			signEntity.setNickDec(this.encryptService.decryptAES256(signEntity.getNick()));
		} catch (Exception e) {
		}
		
		return signEntity;
	}

}
