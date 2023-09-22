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
public enum StyleAnswerType {

    type1("시크한", "자네에게는 시크한 스타일을 추천하네!", "위처럼 시크한 스타일로 자신을 맘껏 꾸며보시게!", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "style_result_34.png"),
    type2("심플한", "자네에게는 심플한 스타일을 추천하네!", "위처럼 심플한 스타일로 자신을 맘껏 꾸며보시게!", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "style_result_33.png"),
    type3("유니크한", "자네에게는 유니크한 스타일을 추천하네!", "위처럼 유니크한 스타일로 자신을 맘껏 꾸며보시게!", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "style_result_31.png"),
    type4("차분한", "자네에게는 차분한 스타일을 추천하네!", "위처럼 러블리한 스타일로 자신을 맘껏 꾸며보시게!", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "style_result_32.png");

    private String title;
    private String recommend;
    private String desc;
    private String todo;
    private String image;

    public String getName() {
        return this.name();
    }

    public static StyleAnswerType from(String name) {
        return EnumUtils.getEnumFromString(StyleAnswerType.class, name);
    }
}
