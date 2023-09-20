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
public enum PursueAnswerType {
    
    type1("소박함", "작은 행복에 집중하는 성향", "심플하고 소소함을 원함"),
    type2("편안함", "심신의 안정에 집중하는 성향", "부드럽고 무난함을 원함"),
    type3("아늑함", "따뜻한 감성을 추구하는 성향", "넉넉하고 가득 찬 느낌을 원함"),
    type4("안정감", "현상유지에 집중하는 성향", "무게감있고 단단함을 원함"),
	type5("화려함", "보여지는것에 집중하는 성향", "눈에 확 띄는 것을 원함"),
	type6("독창성", "창의적이고 도전적인 성향", "기존에 없던 것들을 원함"),
	type7("실용성", "현실적이고 합리적인 성향", "기능과 가성비를 중시");

    private String title;
    private String recommend;
    private String desc;

    public String getName() {
        return this.name();
    }

    public static PursueAnswerType from(String name) {
        return EnumUtils.getEnumFromString(PursueAnswerType.class, name);
    }
}
