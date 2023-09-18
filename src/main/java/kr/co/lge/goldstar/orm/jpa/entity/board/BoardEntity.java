/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.board;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Entity(name="board")
@Table(name="tb_bbsctt")
@Data
public class BoardEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7462640374032275820L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "bbs_sn")
    private int bbsSn;
    
    @Column(name = "delete_at")
    @Enumerated(EnumType.STRING)
    private YesOrNo deleted;
    
    @Column(name = "ordinal")
    private int order;
    
    @Column(name = "sj")
    private String subject;
    
    @Column(name = "cn")
    private String contents;
    
    @Column(name = "rd")
    private int read;
    
	@Column(name = "regist_dt")
	private String created;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BBS_SN", insertable = false, updatable = false)
    private BbsEntity bbs;
    
    @Transient
    private boolean newIcon;
    
    @Transient
    private String contentsBr;
}
