/**
 * 
 */
package kr.co.lge.goldstar.orm.jpa.entity.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import kr.co.lge.goldstar.orm.jpa.entity.PursueAnswerType;
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
@Entity(name="product")
@Table(name="tb_product")
@Data
public class ProductEntity implements Serializable {

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
    
    @Column(name = "nm")
    private String name;
    
    @Column(name = "ordinal")
    private int ordinal;
    
    @Column(name = "image_list")
    private String imageList;
    
    @Column(name = "image_view")
    private String imageView;
    
    @Column(name = "list_ct")
    private String listContentType;
    
    @Column(name = "view_ct")
    private String viewContentType;
    
    @Column(name = "sj")
    private String subject;
    
    @Column(name = "cn")
    private String contents;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "ty")
    @Enumerated(EnumType.STRING)
    private PursueAnswerType type;
}
