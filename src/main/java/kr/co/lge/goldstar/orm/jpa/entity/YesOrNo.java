/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity;

import kr.co.rebel9.core.utils.EnumUtils;
import lombok.AllArgsConstructor;

/**
 * @author issuettl
 *
 */
@AllArgsConstructor
public enum YesOrNo {

    Y,N;

    /**
     * @return the name
     */
    public String getTitle() {
        return this.name();
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name();
    }
    
    /**
     * @param name of YesOrNo
     * @return YesOrNo
     */
    public static YesOrNo from(String name) {
        return EnumUtils.getEnumFromString(YesOrNo.class, name);
    }
}
