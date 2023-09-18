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
public enum StaffCheck {

	notyet("미증정", "체험전"),
    present("증정", "체험완료");

    private String title;
    private String life;

    public String getName() {
        return this.name();
    }

    public static StaffCheck from(String name) {
        return EnumUtils.getEnumFromString(StaffCheck.class, name);
    }
    
}
