/**
 * 
 */
package kr.co.lge.goldstar.mvc.u.sign.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import kr.co.lge.goldstar.mvc.c.encrypt.service.EncryptService;
import kr.co.lge.goldstar.mvc.u.sign.service.SignService;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignId;
import kr.co.lge.goldstar.orm.jpa.repository.spring.MemberRepository;
import kr.co.lge.goldstar.orm.jpa.repository.spring.SignRepository;
import kr.co.rebel9.core.utils.DateUtils;
import kr.co.rebel9.core.utils.StringUtils;
import kr.co.rebel9.web.data.DataMap;
import kr.co.rebel9.web.utils.RequestUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author issuettl
 *
 */
@Slf4j
@Service("u.SignService")
public class SignServiceImpl implements SignService {
	
	private static final String SIGN_KEY = "AUTH-SIGN";
	private static final String MEMBER_KEY = "AUTH-MEMBER";

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	private SignRepository signRepository;

	@Override
	public boolean existSignIn() {
		
		HttpSession session = RequestUtils.getRequest().getSession();
		
		SignEntity signEntity = (SignEntity)session.getAttribute(SIGN_KEY); 
		if(ObjectUtils.isEmpty(signEntity)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean existMemberIn() {
		
		HttpSession session = RequestUtils.getRequest().getSession();
		
		MemberEntity memberEntity = (MemberEntity)session.getAttribute(MEMBER_KEY); 
		if(ObjectUtils.isEmpty(memberEntity)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkSignIn() {
		
		if(!existMemberIn()) {
			return false;
		}
		
		MemberEntity member = getMemberIn();
		SignId signId = new SignId();
		signId.setMemberSn(member.getSn());
		signId.setCreated(DateUtils.getToday("yyyyMMdd"));
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		
		if(signEntity.isEmpty()) {
			return false;
		}
		
		signInSession(signEntity.get());
		
		return true;
	}

	/**
	 * @param signEntity
	 */
	public void signInSession(SignEntity signEntity) {
		HttpSession session = RequestUtils.getRequest().getSession();
		
		try {
			signEntity.setNickDec(this.encryptService.decryptAES256(signEntity.getNick()));
		} catch (Exception e) {
		}
		
		session.setAttribute(SIGN_KEY, signEntity);
	}
	
	@Override
	public void signInMember(DataMap params, DataMap data) {
		
		//params -> account
		try {
			HttpSession session = RequestUtils.getRequest().getSession();
			
			String uid = data.getAsString("unifyId");
			String name = this.encryptService.encryptAES256(data.getAsString("mbrNm"));
			String phone = this.encryptService.encryptAES256(data.getAsString("cphn"));

			MemberEntity member = this.memberRepository.findByUid(uid);

			if(ObjectUtils.isEmpty(member)) { //과거 회원정보
				member = this.memberRepository.findByUid(data.getAsString("empMbrNo"));
			}
			
			if(ObjectUtils.isEmpty(member)) {
				member = new MemberEntity();
				member.setCreated(DateUtils.getToday("yyyyMMddHHmmss"));
			}

			member.setUid(uid);
			member.setName(name);
			member.setPhone(phone);
			member.setUpdated(DateUtils.getToday("yyyyMMddHHmmss"));
			
			this.memberRepository.save(member);
 
			member.setNameDec(data.getAsString("mbrNm"));
			member.setPhoneDec(data.getAsString("cphn"));
			
			DataMap decrypt = params.getAsDataMap("decrypt");
			
			member.setAccessToken(decrypt.getAsString("accessToken"));
			member.setRefreshToken(decrypt.getAsString("refreshToken"));
			
			member.setAccessTokenEnc(params.getAsString("accessToken"));
			member.setUnifyIdEnc(params.getAsString("unifyId"));
			
			session.setAttribute(MEMBER_KEY, member);
		} catch (Exception e) {
			params.put("signInMemberException", e.getMessage());
		}
	}
	
	@Override
	public void signInMember(MemberEntity member) {
		
		HttpSession session = RequestUtils.getRequest().getSession();
		
		//세션 회원체크
		MemberEntity signed = getMemberIn();
		
		member.setNameDec(signed.getNameDec());
		member.setPhoneDec(signed.getPhoneDec());
		
		member.setAccessToken(signed.getAccessToken());
		member.setRefreshToken(signed.getRefreshToken());
		
		member.setAccessTokenEnc(signed.getAccessTokenEnc());
		member.setUnifyIdEnc(signed.getUnifyIdEnc());
		
		session.setAttribute(MEMBER_KEY, member);
	}

	@Override
	public DataMap signIn(SignEntity signEntity) {
		
		if(!existMemberIn()) {
			DataMap result = new DataMap(false);
			result.put("reason", "LG 회원이 아닙니다.");
			return result;
		}

		if(existSignIn()) {
			DataMap result = new DataMap(false);
			result.put("reason", "이미 닉네임을 등록했습니다.");
			result.put("code", 1001);
			return result;
		}
		
		MemberEntity member = getMemberIn();
		signEntity.setMember(member);
		
		try {
			signEntity.setNick(this.encryptService.encryptAES256(signEntity.getNick()));
		} catch (Exception e) {
		}
		signEntity.getId().setCreated(DateUtils.getToday("yyyyMMdd"));
		signEntity.setAdded(DateUtils.getToday("yyyyMMddHHmmss"));
		
		String today = DateUtils.getToday("yyyyMMdd");
		if(StringUtils.isEmpty(signEntity.getId().getCreated())) {
			signEntity.getId().setCreated(today);
		}

		try {
			this.signRepository.save(signEntity);
		}catch(Exception e) {
			e.printStackTrace();
			log.error(signEntity.toString());
		}
		
		signInSession(signEntity);
		
		DataMap result = new DataMap(true);
		result.put("sign", signEntity);
		
		return result;
	}

	/**
	 * @return
	 */
	@Override
	public MemberEntity getMemberIn() {
		return (MemberEntity)RequestUtils.getRequest().getSession().getAttribute(MEMBER_KEY);
	}

	@Override
	public SignEntity getSignIn() {
		return (SignEntity)RequestUtils.getRequest().getSession().getAttribute(SIGN_KEY);
	}

	@Override
	public void signOut() {
		RequestUtils.getRequest().getSession().removeAttribute(SIGN_KEY);
	}

	@Override
	public void memberOut() {
		RequestUtils.getRequest().getSession().removeAttribute(SIGN_KEY);
		RequestUtils.getRequest().getSession().removeAttribute(MEMBER_KEY);
	}

	@Override
	public void signRemove() {
		
		if(!existMemberIn()) {
			return;
		}
		
		MemberEntity member = getMemberIn();
		SignId signId = new SignId();
		signId.setMemberSn(member.getSn());
		signId.setCreated(DateUtils.getToday("yyyyMMdd"));
		
		Optional<SignEntity> signEntity = this.signRepository.findById(signId);
		
		if(signEntity.isEmpty()) {
			return;
		}
		
		this.signRepository.delete(signEntity.get());
		
		signOut();
	}

	@Override
	public DataMap saveWorry(SignEntity signEntity) {
		
		SignEntity signIn = getSignIn();
		
		Optional<SignEntity> signSaved = this.signRepository.findById(signIn.getId());
		if(signSaved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "닉네임 정보를 찾을 수 없습니다.");
			return result;
		}
		
		SignEntity saved = signSaved.get();
		saved.setWorryType(signEntity.getWorryType());
		
		this.signRepository.save(saved);
		saved.getMember();
		
		signInSession(saved);
		
		return new DataMap(true);
	}

	@Override
	public DataMap savePursue(SignEntity signEntity) {
		
		SignEntity signIn = getSignIn();
		
		Optional<SignEntity> signSaved = this.signRepository.findById(signIn.getId());
		if(signSaved.isEmpty()) {
			DataMap result = new DataMap(false);
			result.put("reason", "닉네임 정보를 찾을 수 없습니다.");
			return result;
		}
		
		SignEntity saved = signSaved.get();
		saved.setPursueType(signEntity.getPursueType());
		
		this.signRepository.save(saved);
		saved.getMember();
		
		signInSession(saved);
		
		return new DataMap(true);
	}

	@Override
	public SignEntity getSignEntity() {

		SignEntity signIn = getSignIn();
		
		Optional<SignEntity> signSaved = this.signRepository.findById(signIn.getId());
		if(signSaved.isEmpty()) {
			return null;
		}
		
		signIn = signSaved.get();
		
		try {
			signIn.setNickDec(this.encryptService.decryptAES256(signIn.getNick()));
		} catch (Exception e) {
		}
		
		return signSaved.get();
	}

	@Override
	public MemberEntity getMemberEntity(int memberSn) {
		
		Optional<MemberEntity> memberEntity = this.memberRepository.findById(memberSn);
		if(memberEntity.isEmpty()) {
			return null;
		}
		
		return memberEntity.get();
	}
}
