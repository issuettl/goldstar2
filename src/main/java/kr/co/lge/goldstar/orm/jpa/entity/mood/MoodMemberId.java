package kr.co.lge.goldstar.orm.jpa.entity.mood;

import java.io.Serializable;

import javax.persistence.Embeddable;

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
public class MoodMemberId implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -1810829538656738710L;

	private int partSn;
	private int answerSn;
}
