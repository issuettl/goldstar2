/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity(name="managerLog")
@Table(name="tb_manager_log")
@Data
public class ManagerLogEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
	@Column(name = "created")
	private String created;
    
	@Column(name = "ip")
	private String ip;
    
	@Column(name = "success_yn")
    @Enumerated(EnumType.STRING)
    private YesOrNo success;
    
    @Column(name = "manager_sn")
    private int managerSn;
    
    @Column(name = "manager_nm")
    private String managerName;
    
    @Column(name = "manager_company")
    private String managerCompany;
    
    @Column(name = "menu_nm")
    private String menuName;
    
	@Column(name = "button_nm")
	private String buttonName;
	
	@Column(name = "process_nm")
    private String processName;
	
	@Column(name = "param_data")
    private String params;
	
	@Column(name = "result_data")
    private String result;
	
	@Column(name = "started")
    private String started;
	
	@Column(name = "finished")
    private String finished;
}
