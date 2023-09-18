/**
 * 
 */
package kr.co.lge.goldstar.core.config.database;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author issuettl
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"kr.co.lge.goldstar.orm.jpa.repository"})
public class DatabaseJpaConfiguration {
	
	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;

	@Value("${hibernate.format_sql}")
	private String hibernateFormatSql;

	@Value("${hibernate.use_sql_comments}")
	private String hibernateUseSqlComments;

	@Value("${hibernate.id.new_generator_mappings}")
	private String hibernateIdNewGeneratorMappings;
	
	@Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("kr.co.lge.goldstar.orm.jpa.entity");
        entityManagerFactoryBean.setPersistenceUnitName("name");
 
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", this.hibernateDialect);
        jpaProperties.put("hibernate.show_sql", this.hibernateShowSql);
        jpaProperties.put("hibernate.format_sql", this.hibernateFormatSql);
        jpaProperties.put("hibernate.use_sql_comments", this.hibernateUseSqlComments);
        jpaProperties.put("hibernate.id.new_generator_mappings", this.hibernateIdNewGeneratorMappings);
 
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
 
        return entityManagerFactoryBean;
    }
}
