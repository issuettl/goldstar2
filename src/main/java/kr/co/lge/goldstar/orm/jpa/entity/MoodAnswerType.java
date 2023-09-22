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
public enum MoodAnswerType {
    
    type1("", "LG OLED의 크고 생생한 화면으로 레트로 게임을 즐겨보시게!", "", "기분고침 코-너에 있는 테블릿에 QR을 스캔해보게 체험을 완료할 수 있다네!", "mind_result_21.png"),
    type2("", "LG OLED의 크고 생생한 화면으로 레트로 게임을 즐겨보시게!", "", "기분고침 코-너에 있는 테블릿에 QR을 스캔해보게 체험을 완료할 수 있다네!", "mind_result_21.png"),
    type3("", "LG OLED의 크고 생생한 화면으로 레트로 게임을 즐겨보시게!", "", "기분고침 코-너에 있는 테블릿에 QR을 스캔해보게 체험을 완료할 수 있다네!", "mind_result_21.png"),
    type4("", "LG OLED의 크고 생생한 화면으로 레트로 게임을 즐겨보시게!", "", "기분고침 코-너에 있는 테블릿에 QR을 스캔해보게 체험을 완료할 수 있다네!", "mind_result_21.png");

    private String title;
    private String recommend;
    private String desc;
    private String todo;
    private String image;

    public String getName() {
        return this.name();
    }

    public static MoodAnswerType from(String name) {
        return EnumUtils.getEnumFromString(MoodAnswerType.class, name);
    }
}
