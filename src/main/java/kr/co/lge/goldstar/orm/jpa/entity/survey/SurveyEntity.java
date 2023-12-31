/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.survey;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Where;

import kr.co.lge.goldstar.orm.jpa.entity.YesOrNo;
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
@Entity(name="survey")
@Table(name="tb_survey")
@Data
public class SurveyEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "question")
    private String question;
    
    @Column(name = "ordinal")
    private int ordinal;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private YesOrNo status;
    
    @Column(name = "delete_at")
    @Enumerated(EnumType.STRING)
    private YesOrNo deleted;

    @OneToMany(
            mappedBy = "survey",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY,
            orphanRemoval = true)
    @Where(clause = "delete_at = 'N'")
    @OrderBy("ordinal ASC")
    private List<SurveyAnswerEntity> answers;
    
    @Transient
    private long total;

	@Override
	public String toString() {
		return "SurveyEntity [sn=" + sn + ", question=" + question + ", ordinal=" + ordinal + ", status=" + status + "]";
	}
}
