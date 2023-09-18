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
public enum LifeTime {
    
    AM11("1100","11:00"),
    PM12("1200", "12:00"),
    PM13("1300", "13:00"),
    PM14("1400", "14:00"),
    PM15("1500", "15:00"),
    PM16("1600", "16:00"),
    PM17("1700", "17:00"),
    PM18("1800", "18:00");

    private String time;
    private String fmt;

    public String getName() {
        return this.name();
    }

    public static LifeTime from(String name) {
        return EnumUtils.getEnumFromString(LifeTime.class, name);
    }
}
