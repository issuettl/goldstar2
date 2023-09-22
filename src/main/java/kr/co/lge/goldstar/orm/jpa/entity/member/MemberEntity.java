/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

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
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.co.lge.goldstar.orm.jpa.entity.AgeType;
import kr.co.lge.goldstar.orm.jpa.entity.GenderType;
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
@Entity(name="member")
@Table(name="tb_member")
@Data
public class MemberEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "id")
    private String uid;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "hp")
    private String phone;
    
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    
    @Column(name = "age")
    @Enumerated(EnumType.STRING)
    private AgeType age;
    
	@Column(name = "created")
	private String created;
    
	@Column(name = "updated")
	private String updated;
    
	@Column(name = "updated_gender")
	private String updatedGender;
    
	@Column(name = "updated_age")
	private String updatedAge;
    
	@Column(name = "updated_personal")
	private String updatedPersonal;
    
    @Column(name = "jammy_sn")
    private int jammySn;
    
    @Transient
    private long visit;
    
    @Transient
    private long part;
    
    @Transient
    private String nameDec;
    
    @Transient
    private String phoneDec;
    
    @Transient
    private String accessToken;
    
    @Transient
    private String refreshToken;
    
    @Transient
    private String accessTokenEnc;
    
    @Transient
    private String unifyIdEnc;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "jammy_sn", insertable = false, updatable = false)
    private JammyEntity jammy;

	@Override
	public String toString() {
		return "MemberEntity [sn=" + sn + ", uid=" + uid + ", name=" + name + ", phone=" + phone + ", created=" + created + "]";
	}
}
