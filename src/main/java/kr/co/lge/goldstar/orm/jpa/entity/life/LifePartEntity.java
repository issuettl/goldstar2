/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.life;

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

import kr.co.lge.goldstar.orm.jpa.entity.LifeStatus;
import kr.co.lge.goldstar.orm.jpa.entity.LifeTime;
import kr.co.lge.goldstar.orm.jpa.entity.LifeType;
import kr.co.lge.goldstar.orm.jpa.entity.StaffCheck;
import kr.co.lge.goldstar.orm.jpa.entity.member.MemberEntity;
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
@Entity(name="lifePart")
@Table(name="tb_life_part")
@Data
public class LifePartEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;

    @Column(name = "member_sn")
    private int memberSn;
    
    @Column(name = "ty")
    @Enumerated(EnumType.STRING)
    private LifeType type;
    
	@Column(name = "member_cnt")
	private int memberCount;
    
	@Column(name = "date")
	private String date;
    
	@Column(name = "time")
    @Enumerated(EnumType.STRING)
	private LifeTime time;
    
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LifeStatus status;
    
	@Column(name = "reservated")
	private String reservated;
    
	@Column(name = "canceled")
	private String canceled;
    
	@Column(name = "reservated_admin")
	private String reservatedAdmin;
    
	@Column(name = "canceled_admin")
	private String canceledAdmin;
    
    @Column(name = "staff_created")
    private String staffCreated;
    
    @Column(name = "staff_check")
    @Enumerated(EnumType.STRING)
    private StaffCheck staffCheck;
    
	@Column(name = "memo")
	private String memo;
	
	@Transient
	private String statusName;

    @JsonIgnore
    @MapsId("memberSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_sn", insertable = false, updatable = false)
    private MemberEntity member;
}
