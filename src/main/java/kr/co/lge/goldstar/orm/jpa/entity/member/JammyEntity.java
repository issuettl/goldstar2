/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author issuettl
 *
 */
@Entity(name="jammy")
@Table(name="tb_jammy")
@Data
public class JammyEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7211230751776938474L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sn")
    private int sn;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "issued")
    private String issued;
}
