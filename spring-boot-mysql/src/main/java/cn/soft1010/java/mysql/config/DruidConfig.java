package cn.soft1010.java.mysql.config;

import java.util.Properties;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
@MapperScan(value = "cn.soft1010.**.dao.**", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class DruidConfig {

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlBean = new SqlSessionFactoryBean();
        sqlBean.setDataSource(dataSource);

        // 分页插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialet", "mysql");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "pageNum=page;pageSize=pageSize;");
        pageInterceptor.setProperties(properties);
        sqlBean.setPlugins(new Interceptor[]{pageInterceptor});
        sqlBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/**/*.xml"));

        return sqlBean.getObject();
    }

    @Bean(name = "mysqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DruidDataSource dateSource() {
        return new DruidDataSource();
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory
                                                             sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
