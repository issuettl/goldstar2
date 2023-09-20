/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.survey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * TODO desc
 * @author data@rebel9.co.kr JungKyung Park
 * @since Jun 26, 2019
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------		--------	---------------------------
 *  Jun 26, 2019		박정경		최초 생성
 */
@Entity(name="surveyMember")
@Table(name="tb_survey_member")
@Data
public class SurveyMemberEntity implements Serializable {
    
    /**
     * 
     */
    public SurveyMemberEntity() {
        this.id = new SurveyMemberId();
    }
    
    private static final long serialVersionUID = 5623830977450431211L;

    @EmbeddedId
    private SurveyMemberId id;
    
	@Column(name = "created")
	private String created;

    @JsonIgnore
    @MapsId("partSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_sn", insertable = false, updatable = false)
    private SurveyPartEntity part;

    @JsonIgnore
    @MapsId("answerSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_sn", insertable = false, updatable = false)
    private SurveyAnswerEntity answer;
}
