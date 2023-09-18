/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.event;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Entity(name="event")
@Table(name="tb_event")
@Data
public class EventEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7462640374032275820L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "delete_at")
    @Enumerated(EnumType.STRING)
    private YesOrNo deleted;
    
    @Column(name = "s_date")
    private String startDate;
    
    @Column(name = "e_date")
    private String endDate;
    
    @Column(name = "sj")
    private String subject;
    
    @Column(name = "cn")
    private String contents;
    
    @Column(name = "thumb")
    private String thumb;
    
    @Column(name = "pc_view")
    private String pcView;
    
    @Column(name = "mo_view")
    private String moView;
    
    @Column(name = "thumb_ct")
    private String thumbContentType;
    
    @Column(name = "pc_view_ct")
    private String pcViewContentType;
    
    @Column(name = "mo_view_ct")
    private String moViewContentType;
    
	@Column(name = "created")
	private String created;
    
    @Transient
    private String contentsBr;
}
