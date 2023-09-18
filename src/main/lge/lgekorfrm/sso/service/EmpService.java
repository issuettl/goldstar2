package lgekorfrm.sso.service;

import lgekorfrm.sso.emp.EmpIntegrateResponse;

public interface EmpService {

    /**
     * ThinQ앱 <-> 한영본앱 통합 여부 조회 (MR.024)
     * GET /account/user/matching/integrated
     * param itg_user_type=B&id_tp_code=LGE&src_svc_code=SVC202&svc_code_list=SVC612&user_no=KR2012334421
     * { "account": { "country": "KR", // 계정의 국가 "usrNo": "KR202009080001", // EMP 회원번호 "svcUsrId": "30caa201b60aa774da894380d0f00007" // 통합 서비스 번호 } }
     * { "error" : { "request" : “/emp/v2.0/account/user/matching/integrated”, "code" : " MR.024.24", "message" : " Suitable Account[itg_trgt_user_no:통합대상UserNo, itg_trgt_except_user_no:통합대상제외UserNo] } }
     * @param entity
     * @return
     */
    public EmpIntegrateResponse callEmpAccountMatchingIntegrate(String thinqIdTpCode, String thinqEmpUserNo) throws Exception;
}
