/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Entity(name="ManagerAuth")
@Table(name="tb_manager_auth")
@Data
public class ManagerAuthEntity implements Serializable {
    
    /**
     * 
     */
    public ManagerAuthEntity() {
        this.id = new ManagerAuthId();
    }
    
    private static final long serialVersionUID = 5623830977450431211L;

    @EmbeddedId
    private ManagerAuthId id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @MapsId("managerSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_sn", insertable = false, updatable = false)
    private ManagerEntity manager;
}
