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
public enum AgeType {
    
    type1("10대"),
    type2("20대"),
    type3("30대"),
    type4("40대"),
    type5("50대"),
    type6("60대이상");

    private String title;

    public String getName() {
        return this.name();
    }

    public static AgeType from(String name) {
        return EnumUtils.getEnumFromString(AgeType.class, name);
    }
}
