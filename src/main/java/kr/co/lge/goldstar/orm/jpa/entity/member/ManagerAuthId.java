package kr.co.lge.goldstar.orm.jpa.entity.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import kr.co.lge.goldstar.orm.jpa.entity.ManagerAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author cerakoted, base@rebel9.co.kr, cerakoted@gmail.com
 * @since 1.0
 * @date 2019. 06. 18.
 *
 */

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerAuthId implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -1810829538656738710L;

	private int managerSn;
	
	@Column(name = "auth")
    @Enumerated(EnumType.STRING)
    private ManagerAuth auth;
}
