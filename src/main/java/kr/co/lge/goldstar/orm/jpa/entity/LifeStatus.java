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
public enum LifeStatus {
    
    status1("", "응모중"),
    status2("예약", "당첨"),
    status3("", "미당첨"),
    status4("체험완료", "체험완료"),
    status5("취소", "취소"),
    status6("권한삭제", "권한삭제");

    private String weekday;
    private String weekend;

    public String getName() {
        return this.name();
    }

    public static LifeStatus from(String name) {
        return EnumUtils.getEnumFromString(LifeStatus.class, name);
    }
    
}
