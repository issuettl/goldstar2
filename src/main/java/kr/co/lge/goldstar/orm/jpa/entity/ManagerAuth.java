/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity;

import kr.co.rebel9.core.utils.EnumUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author issuettl
 *
 */
@Getter
@AllArgsConstructor
public enum ManagerAuth {
    
    a01member("회원리스트"),
    a02survey("사전문답 관리"),
    a03event("이벤트 관리"),
    a04faq("FAQ 관리"),
    a05board("공지사항 관리"),
    a06life("방탈출 관리"),
    a07manager("관리자 관리");

    private String title;

    public String getName() {
        return this.name();
    }

    public static ManagerAuth from(String name) {
        return EnumUtils.getEnumFromString(ManagerAuth.class, name);
    }
}
