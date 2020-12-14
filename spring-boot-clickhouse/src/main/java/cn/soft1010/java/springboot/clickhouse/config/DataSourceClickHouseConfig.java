package cn.soft1010.java.springboot.clickhouse.config;

import com.alibaba.druid.pool.DruidDataSource;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by bjzhangjifu on 2020/12/1.
 */
@Configuration
@MapperScan(basePackages = "cn.soft1010.**.dao.**", sqlSessionTemplateRef = "clickHouseSqlSessionTemplate")
public class DataSourceClickHouseConfig {

    @Bean(name = "clickHouseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.clickhouse")
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "clickHouseSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory(@Qualifier("clickHouseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlBean = new SqlSessionFactoryBean();
        sqlBean.setDataSource(dataSource);
        sqlBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper-clickHouse/**/*.xml"));
        return sqlBean.getObject();
    }

    @Bean(name = "clickHouseSqlSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("clickHouseSqlSessionFactory") SqlSessionFactory sqlSessionFactory)
            throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
