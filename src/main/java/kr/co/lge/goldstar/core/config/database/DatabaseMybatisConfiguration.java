/**
 * 
 */
package kr.co.lge.goldstar.core.config.database;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author issuettl
 *
 */
@Configuration
@MapperScan("kr.co.lge.goldstar.orm.dynamic.persistence")
@EnableTransactionManagement
public class DatabaseMybatisConfiguration {
	
	/**
	 * @param dataSource 
	 * @return 세션 팩토리
	 * @throws IOException 
     */
    @Bean
    public SqlSessionFactoryBean sessionFactory(DataSource dataSource) throws IOException {
    	
    	System.out.println("-----------------");
        
    	SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("kr.co.lge.goldstar.orm.dynamic.domain");
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("kr/co/lge/goldstar/orm/dynamic/mapper.xml"));
		sqlSessionFactoryBean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath*:kr/co/lge/goldstar/orm/dynamic/mapper/*.xml"));
		
        return sqlSessionFactoryBean;
    }

    /**
     * @param sessionFactory 
     * @return SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sessionTemplate(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }
}
