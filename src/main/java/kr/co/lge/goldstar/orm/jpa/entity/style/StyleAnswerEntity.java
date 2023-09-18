/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.style;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.lge.goldstar.orm.jpa.entity.StyleAnswerType;
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
@Entity(name="styleAnswer")
@Table(name="tb_style_answer")
@Data
public class StyleAnswerEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "style_sn")
    private int styleSn;
    
    @Column(name = "delete_at")
    @Enumerated(EnumType.STRING)
    private YesOrNo deleted;
    
    @Column(name = "nm")
    private String name;
    
    @Column(name = "ty")
    @Enumerated(EnumType.STRING)
    private StyleAnswerType type;

    @JsonIgnore
    @MapsId("styleSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_sn", insertable = false, updatable = false)
    private StyleEntity style;
    
    @Transient
    private long count;
}
