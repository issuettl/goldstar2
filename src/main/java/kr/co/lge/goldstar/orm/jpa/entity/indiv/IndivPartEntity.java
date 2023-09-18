/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.indiv;

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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import kr.co.lge.goldstar.orm.jpa.entity.IndivAnswerType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.SignEntity;
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
@Entity(name="indivPart")
@Table(name="tb_indiv_part")
@Data
public class IndivPartEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
	@Column(name = "created")
	private String created;
    
    @Column(name = "ty")
    @Enumerated(EnumType.STRING)
    private IndivAnswerType type;
    
    @Column(name = "sign_member_sn")
    private int signMemberSn;
    
    @Column(name = "sign_created")
    private String signCreated;
    
    @Column(name = "staff_created")
    private String staffCreated;
    
    @Column(name = "staff_check")
    @Enumerated(EnumType.STRING)
    private StaffCheck staffCheck;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({
        @JoinColumn(name = "sign_member_sn", referencedColumnName="member_sn", insertable = false, updatable = false),
        @JoinColumn(name = "sign_created", referencedColumnName="created", insertable = false, updatable = false)
        })
    private SignEntity sign;
}
