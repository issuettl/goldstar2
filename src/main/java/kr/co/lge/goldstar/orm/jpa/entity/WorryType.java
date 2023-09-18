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
public enum WorryType {
    
    type1("관계", "믿음과 신뢰의 고민", "cert_relation_", "마음을 둘 곳이 없어 답답해요", new CornerType[] {CornerType.mind, CornerType.style}, new CornerType[] {CornerType.indiv, CornerType.refresh, CornerType.mood, CornerType.life}),
    type2("일상", "치열한 일상의 고민", "cert_life_", "일상이 너무 치열해요", new CornerType[] {CornerType.style, CornerType.mood}, new CornerType[] {CornerType.mind, CornerType.indiv, CornerType.life, CornerType.refresh}),
    type3("건강", "건강한 삶에 대한 고민", "cert_health_", "몸과 마음이 지쳤어요", new CornerType[] {CornerType.mind, CornerType.indiv}, new CornerType[] {CornerType.refresh, CornerType.style, CornerType.mood, CornerType.life}),
    type4("미래", "미래에 대한 고민", "cert_future_", "미래가 막막하게 느껴져요", new CornerType[] {CornerType.mind, CornerType.indiv}, new CornerType[] {CornerType.style, CornerType.mood, CornerType.life, CornerType.refresh}),
    type5("자신감", "도전과 용기에 대한 고민", "cert_confidence_", "새로운 도전이 두려워요", new CornerType[] {CornerType.mood, CornerType.style}, new CornerType[] {CornerType.mind, CornerType.indiv, CornerType.life, CornerType.refresh}),
    type6("도전", "잃어버린 꿈에 대한 고민", "cert_challenge_", "꿈과 희망을 잃어버렸어요", new CornerType[] {CornerType.indiv, CornerType.mind}, new CornerType[] {CornerType.style, CornerType.mood, CornerType.life, CornerType.refresh});

    private String title;
    private String desc;
    private String cert;
    private String certText;
    private CornerType[] recommend;
    private CornerType[] normal;

    public String getName() {
        return this.name();
    }

    public static WorryType from(String name) {
        return EnumUtils.getEnumFromString(WorryType.class, name);
    }
    
}
