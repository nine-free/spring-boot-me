
## spring boot 系列 --> spring boot&mybatis&druid连接mysql

#### 1、首先新建一个maven项目
参考 https://juejin.cn/post/6904940907045322766
#### 2、添加依赖 mysql驱动&druid&mybatis
![](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/08bcbee2278c4c6d9139b158e4f06458~tplv-k3u1fbpfcp-watermark.image)
#### 3、druid数据源配置
##### 3.1、这里直接使用class类
```
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
```
##### 3.2、使用到的druid配置属性 可以直接添加到application.yml中
这里我们新建一个application-druid.yml 添加配置
```
spring:
  datasource:
    druid:
      url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&
      username: root
      password: zhang1019
      driver-class-name: com.mysql.cj.jdbc.Driver
      initial-size: 5
      max-active: 10
      min-idle: 1
      max-wait: 1000
```
application.yml 包含这个文件
```
spring:
  profiles:
    include: druid
```
注意：只能是application-*.yml,其他文件不能识别，include：*

#### 接下来添加对应的Dao接口 和mapper.xml的mybatis配置文件
UserDao.class
```
public interface UserDao {
    User queryUserByName(@Param("name") String name);
}
```
UserMapper.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="cn.soft1010.java.mysql.dao.UserDao">

    <select id="queryUserByName" resultType="cn.soft1010.java.mysql.model.User">
      select id,name,age,note from t_user where name = #{name,jdbcType=VARCHAR}
    </select>

</mapper>

```
#### 4、验证一下结果 junit测试
```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationRunner.class)
public class UserDaoTest {

    @Resource
    private UserDao userDao;

    @Test
    public void test() {
        User user = userDao.queryUserByName("张三");
        Assert.assertEquals("张三", user.getName());
        System.out.println(JSONObject.toJSON(user));
    }
}
```

#### 5、再追加一个单纯的jdbc连接数据库

![](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4e9f64f3d8a44f61ba75df302bf9535f~tplv-k3u1fbpfcp-watermark.image)



