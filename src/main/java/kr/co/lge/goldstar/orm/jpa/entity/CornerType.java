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
public enum CornerType {
    
    mind("마음고침", "마음고침 코-너", "마음고침 코-너"),
    indiv("개성고침", "개성고침 코-너", "개성고침 코-너"),
    style("스타일고침", "스타일고침 코-너", "스타일고침 코-너"),
    mood("기분고침", "기분고침 코-너<br>(금성오락실)", "기분고침 코-너 (금성오락실)"),
    life("고민탈출", "고민탈출 코-너<br>(ThinQ 방탈출)", "고민탈출 코-너 (ThinQ 방탈출)"),
    refresh("새로고침", "새로고침 코-너", "새로고침 코-너");

    private String title;
    private String zone;
    private String zoneLine;

    public String getName() {
        return this.name();
    }

    public static CornerType from(String name) {
        return EnumUtils.getEnumFromString(CornerType.class, name);
    }
    
}
