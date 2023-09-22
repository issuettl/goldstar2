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
public enum GenderType {
    
    type1("남자"),
    type2("여자");

    private String title;

    public String getName() {
        return this.name();
    }

    public static AgeType from(String name) {
        return EnumUtils.getEnumFromString(AgeType.class, name);
    }
}
