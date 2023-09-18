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
public enum MindAnswerType {
    
    type1("엽채류", "자네에게는 푸릇한 엽채류를 추천하네!", "자라나는 채소들로 몸도 마음도 쑥쑥! 더 건강하게 키워보세요.", "QR을 STAFF에게 보여주게!<br/>당신에게 엽채류를 선물해주겠네!", "mind_result_11.png"),
    type2("허브류", "자네에게는 향기로운 허브류를 추천하네!", "향긋한 허브류와 함께면 마음도 쉴 수 있겠죠? 이제 무럭무럭 키워보세요.", "QR을 STAFF에게 보여주게!<br/>당신에게 허브류를 선물해주겠네!", "mind_result_12.png"),
    type3("화훼류", "자네에게는 아름다운 화훼류를 추천하네!", "활짝 핀 꽃 한 송이면 금새 기분이 달라지죠! 자라나는 꽃들을 보며 마음까지 아름다워지세요.", "QR을 STAFF에게 보여주게!<br/>당신에게 화훼류를 선물해주겠네!", "mind_result_13.png");

    private String title;
    private String recommend;
    private String desc;
    private String todo;
    private String image;

    public String getName() {
        return this.name();
    }

    public static MindAnswerType from(String name) {
        return EnumUtils.getEnumFromString(MindAnswerType.class, name);
    }
}
