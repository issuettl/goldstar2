/**
 * 
 */
package kr.co.lge.goldstar.core.repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.querydsl.core.dml.DeleteClause;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAUpdateClause;

/**
 * TODO desc
 * @author data@rebel9.co.kr JungKyung Park
 * @since Jun 27, 2019
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일		수정자		수정내용
 *  -------		--------	---------------------------
 *  Jun 27, 2019		박정경		최초 생성
 * </pre>
 */
@Repository
public abstract class QuerydslRepositorySupport {

    private final PathBuilder<?> builder;

    private @Nullable EntityManager entityManager;
    private @Nullable Querydsl querydsl;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     * 
     * @param domainClass must not be {@literal null}.
     */
    public QuerydslRepositorySupport(Class<?> domainClass) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        this.builder = new PathBuilderFactory().create(domainClass);
    }

    /**
     * Setter to inject {@link EntityManager}.
     * 
     * @param entityManager must not be {@literal null}.
     */
    public void setEntityManager(EntityManager entityManager) {

        Assert.notNull(entityManager, "EntityManager must not be null!");
        this.querydsl = new Querydsl(entityManager, builder);
        this.entityManager = entityManager;
    }

    /**
     * Callback to verify configuration. Used by containers.
     */
    @PostConstruct
    public void validate() {
        Assert.notNull(entityManager, "EntityManager must not be null!");
        Assert.notNull(querydsl, "Querydsl must not be null!");
    }

    /**
     * Returns the {@link EntityManager}.
     * 
     * @return the entityManager
     */
    @Nullable
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Returns a fresh {@link JPQLQuery}.
     * 
     * @param paths must not be {@literal null}.
     * @return the Querydsl {@link JPQLQuery}.
     */
    protected JPQLQuery<Object> from(EntityPath<?>... paths) {
        return getRequiredQuerydsl().createQuery(paths);
    }

    /**
     * Returns a {@link JPQLQuery} for the given {@link EntityPath}.
     * 
     * @param path must not be {@literal null}.
     * @return
     */
    protected <T> JPQLQuery<T> from(EntityPath<T> path) {
        return getRequiredQuerydsl().createQuery(path).select(path);
    }

    /**
     * Returns a fresh {@link DeleteClause}.
     * 
     * @param path
     * @return the Querydsl {@link DeleteClause}.
     */
    protected DeleteClause<JPADeleteClause> delete(EntityPath<?> path) {
        return new JPADeleteClause(getRequiredEntityManager(), path);
    }

    /**
     * Returns a fresh {@link UpdateClause}.
     * 
     * @param path
     * @return the Querydsl {@link UpdateClause}.
     */
    protected UpdateClause<JPAUpdateClause> update(EntityPath<?> path) {
        return new JPAUpdateClause(getRequiredEntityManager(), path);
    }

    /**
     * Returns a {@link PathBuilder} for the configured domain type.
     * 
     * @param <T>
     * @return the Querdsl {@link PathBuilder}.
     */
    @SuppressWarnings("unchecked")
    protected <T> PathBuilder<T> getBuilder() {
        return (PathBuilder<T>) builder;
    }

    /**
     * Returns the underlying Querydsl helper instance.
     * 
     * @return
     */
    @Nullable
    protected Querydsl getQuerydsl() {
        return this.querydsl;
    }

    private Querydsl getRequiredQuerydsl() {

        if (querydsl == null) {
            throw new IllegalStateException("Querydsl is null!");
        }

        return querydsl;
    }

    private EntityManager getRequiredEntityManager() {

        if (entityManager == null) {
            throw new IllegalStateException("EntityManager is null!");
        }

        return entityManager;
    }
}
