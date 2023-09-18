/**
 * 
 */
package kr.co.lge.goldstar.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * @author issuettl, data@rebel9.co.kr
 * @since 1.0
 * @date 2018. 4. 18.
 *
 */
@Repository
public abstract class GoldStarQuerydslRepositorySupport extends QuerydslRepositorySupport {

    /**
     * @param domainClass
     */
    public GoldStarQuerydslRepositorySupport(Class<?> domainClass) {
        super(domainClass);
    }
 
    /**
     * Setter to inject {@link EntityManager}.
     * 
     * @param entityManager must not be {@literal null}.
     */
    @Override
    @PersistenceContext(name = "lge-goldstar")
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
}
