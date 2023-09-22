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
public enum LifeType {
    
    weekday("주중"),
    weekend("주말");

    private String title;

    public String getName() {
        return this.name();
    }

    public static LifeType from(String name) {
        return EnumUtils.getEnumFromString(LifeType.class, name);
    }
    
}
