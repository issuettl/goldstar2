/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.mind;

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
@Entity(name="mind")
@Table(name="tb_mind")
@Data
public class MindEntity implements Serializable {

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
            mappedBy = "mind",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY,
            orphanRemoval = true)
    @OrderBy("sn ASC")
    private List<MindAnswerEntity> answers;
    
    @Transient
    private long total;

	@Override
	public String toString() {
		return "MindEntity [sn=" + sn + ", question=" + question + ", ordinal=" + ordinal + ", status=" + status + "]";
	}
}
