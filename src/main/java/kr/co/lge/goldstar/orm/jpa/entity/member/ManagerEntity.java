/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Entity(name="manager")
@Table(name="tb_manager")
@Data
public class ManagerEntity implements Serializable {

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
    
    @Column(name = "company")
    private String company;
    
    @Column(name = "pw")
    private String password;
    
	@Column(name = "created")
	private String created;
    
	@Column(name = "updated")
	private String updated;
    
	@Column(name = "connected")
	private String connected;
    
	@Column(name = "locked")
	private String locked;
	
	@Column(name = "invalid_pass")
    private int invalidPass;
	
	@Column(name = "updated_pass")
    private String updatedPass;
	
	@Transient
	private String passwordChange;

    @OneToMany(
            mappedBy = "manager",
            cascade = CascadeType.ALL,
            fetch=FetchType.LAZY,
            orphanRemoval = true)
    @OrderBy("id.auth ASC")
    private List<ManagerAuthEntity> authList;

	@Override
	public String toString() {
		return "ManagerEntity [sn=" + sn + ", uid=" + uid + ", name=" + name + ", company=" + company + ", password="
				+ password + ", created=" + created + ", updated=" + updated + ", invalidPass=" + invalidPass
				+ ", updatedPass=" + updatedPass + ", passwordChange=" + passwordChange + "]";
	}
}
