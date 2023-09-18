/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.co.lge.goldstar.orm.jpa.entity.WorryType;
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
@Entity(name="Sign")
@Table(name="tb_sign")
@Data
public class SignEntity implements Serializable {
    
    /**
     * 
     */
    public SignEntity() {
        this.id = new SignId();
    }
    
    private static final long serialVersionUID = 5623830977450431211L;

    @EmbeddedId
    private SignId id;
    
	@Column(name = "nick")
	private String nick;
    
    @Column(name = "worry_ty")
    @Enumerated(EnumType.STRING)
    private WorryType worryType;
	
	@Column(name = "added")
	private String added;
    
    @Transient
    private String nickDec;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @MapsId("memberSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_sn", insertable = false, updatable = false)
    private MemberEntity member;

	@Override
	public String toString() {
		return "SignEntity [id=" + id + ", nick=" + nick + ", worryType=" + worryType + ", added=" + added + "]";
	}
}
