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
public enum IndivAnswerType {
    
    type1("모던", "자네에게는 모던한 디자인을 추천하네!", "이렇게 세련된 스타일로 자네의 그램을 리폼하는 건 어떤가?", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "personality_result_21.png"),
    type2("베이직", "자네에게는 베이직한 디자인을 추천하네!", "이렇게 꾸안꾸 스타일로 자네의 그램을 리폼하는 건 어떤가?", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "personality_result_22.png"),
    type3("트렌디", "자네에게는 트렌디한 디자인을 추천하네!", "이렇게 핫한 스타일로 자네의 그램을 리폼하는 건 어떤가?", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "personality_result_23.png"),
    type4("힙", "자네에게는 힙한 디자인을 추천하네!", "이렇게 쿨한 스타일로 자네의 그램을 리폼하는 건 어떤가?", "QR을 STAFF에게 보여주게!<br/>당신에게 스타일을 바꿀 수 있는 리폼 스티커를 선물해주겠네!", "personality_result_24.png");

    private String title;
    private String recommend;
    private String desc;
    private String todo;
    private String image;

    public String getName() {
        return this.name();
    }

    public static IndivAnswerType from(String name) {
        return EnumUtils.getEnumFromString(IndivAnswerType.class, name);
    }
}
