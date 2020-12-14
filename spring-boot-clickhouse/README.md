---
# 主题列表：juejin, github, smartblue, cyanosis, channing-cyan, fancy, hydrogen, condensed-night-purple, greenwillow, v-green, vue-pro, healer-readable, mk-cute, jzman, geek-black
# 贡献主题：https://github.com/xitu/juejin-markdown-themes
theme: juejin
highlight:clickhouse
---

## clickhouse介绍
一句话总结
```
一个用于联机分析(OLAP)的列式数据库管理系统(DBMS)
```
特点：
```
共享多维信息的快速分析
```
OLAP场景的关键特征 
```
绝大多数是读请求
数据以相当大的批次(> 1000行)更新，而不是单行更新;或者根本没有更新。
已添加到数据库的数据不能修改。
对于读取，从数据库中提取相当多的行，但只提取列的一小部分。
宽表，即每个表包含着大量的列
查询相对较少(通常每台服务器每秒查询数百次或更少)
对于简单查询，允许延迟大约50毫秒
列中的数据相对较小：数字和短字符串(例如，每个URL 60个字节)
处理单个查询时需要高吞吐量(每台服务器每秒可达数十亿行)
事务不是必须的
对数据一致性要求低
每个查询有一个大表。除了他以外，其他的都很小。
查询结果明显小于源数据。换句话说，数据经过过滤或聚合，因此结果适合于单个服务器的RAM中
```
更多clickhouse 介绍参考官方文档
[clickhouse官方文档](https://clickhouse.tech/docs/zh/)

## 首先参考之前的spring boot 系列创建一个项目
下面是我创建的项目目录：
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d72e7a67a619460198fb4314a3a2867f~tplv-k3u1fbpfcp-watermark.image)
## pom.xml添加clickhouse依赖 
这里我们使用jdbc连接
```
	 <dependency>
            <groupId>ru.yandex.clickhouse</groupId>
            <artifactId>clickhouse-jdbc</artifactId>
            <version>${clickhouse.version}</version>
        </dependency>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.3.0</version>
        </dependency>
```
除了clickhouse-jdbc 驱动之外，我们这里查询使用到了分页，所以多了一个pagehelper，其他的依赖我们就不在这里列出了，可以直接参考文章最后的源码

## clickhouse的数据源配置  
我们这里写到了application-clickhouse.yml
有没有发现跟普通mysql数据源配置一致呢
```
spring:
  datasource:
    clickhouse:
      driver-class-name: ru.yandex.clickhouse.ClickHouseDriver
      url: jdbc:clickhouse://mac.zjf:8123/default
      username: default
      password:
      initialSize: 10
      maxActive: 100
      minIdle: 10
      maxWait: 6000
```
## 使用druidDataSource连接clickhouse
这里跟连接mysql数据库类似哦   
但是clickhouse是不支持事务，所以关于事务的配置就省略了。
![](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/9b1f23eec61c45dfbc42b27a358b8322~tplv-k3u1fbpfcp-watermark.image)

## DAO类
我们这里实现了增删查，没有改哦
clickhouse虽然支持insert into table[] values(),();
但是最适用的是序列化文件批量导入
```

public interface UserDao {

    /**
     * 查询
     *
     * @param name
     * @return
     */
    User queryUserByName(String name);

    /**
     * 这里主要用来分页查询
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    List<User> queryUsersByDate(@Param("beginDate") String beginDate,
                                @Param("endDate") String endDate);

    /**
     * 插入
     *
     * @param user
     */
    void saveUser(User user);

    /**
     * 批量插入
     *
     * @param users
     */
    void batchInsertUsers(@Param("users") List<User> users);

    /**
     * 删除
     *
     * @param date
     */
    void deleteUser(@Param("date") String date);
}

```

## Mapper.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="cn.soft1010.java.springboot.clickhouse.dao.UserDao">

    <select id="queryUserByName" resultType="cn.soft1010.java.springboot.clickhouse.model.User">
      select id,name,create_date createDate from t_user where name = #{name,jdbcType=VARCHAR}
    </select>

    <select id="queryUsersByDate" resultType="cn.soft1010.java.springboot.clickhouse.model.User">
            select id,name,create_date createDate
            from t_user
            where create_date >=#{beginDate,jdbcType=VARCHAR}
            and create_date &lt;= #{endDate,jdbcType=VARCHAR}
    </select>

    <insert id="saveUser" parameterType="cn.soft1010.java.springboot.clickhouse.model.User">
        insert into t_user(id,name,create_date) VALUES
        (#{id,jdbcType=VARCHAR},#{name,jdbcType=VARCHAR},toDate(#{createDate,jdbcType=VARCHAR}) )
    </insert>

    <insert id="batchInsertUsers" parameterType="cn.soft1010.java.springboot.clickhouse.model.User">
        insert into t_user(id,name,create_date) VALUES
        <foreach collection="users" item="user" separator=",">
            (#{user.id,jdbcType=VARCHAR},#{user.name,jdbcType=VARCHAR},#{user.createDate,jdbcType=VARCHAR} )
        </foreach>
    </insert>

    <delete id="deleteUser">
        ALTER TABLE t_user DELETE WHERE create_date =  toDate(#{date,jdbcType=VARCHAR})
    </delete>

</mapper>

```

注意：  
1、这里的语句需要根据clickhouse自己的sql语法来   
例如：删除 ALTER TABLE [db.]table DELETE WHERE filter_expr   
2、没有更新语句哦  因为本身clickhouse适用的场景就是批量写，然后聚合读的场景

## Junit测试 验证一下是否可运行
```
	@Test
    public void test() {
        User user1 = new User(1, "张三", "2020-10-01");
        User user2 = new User(2, "李四", "2020-10-01");
        User user3 = new User(3, "王五", "2020-11-01");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        userDao.batchInsertUsers(users);

        User user = userDao.queryUserByName("张三");
        Assert.assertEquals(new Integer(1), user.getId());

        userDao.saveUser(user);
        PageHelper.startPage(1, 3);
        List<User> list = userDao.queryUsersByDate("2020-10-01", "2020-11-01");
        PageInfo pageInfo = new PageInfo(list);
        Assert.assertEquals(pageInfo.getTotal(), 4L);

        userDao.deleteUser("2020-10-01");
        userDao.deleteUser("2020-11-01");

//        User u = userDao.queryUserByName("张三");
//        Assert.assertEquals(u, null);
    }
```
## 特别注意
1、clickhouse的删除操作不是原子操作也不是实时删除了   
就像我的单元测试中的注释掉的两行，大概率是能查询到user的，还可能是多个user   
通过下面的语句能查询具体删除操作的执行情况
```
select database,table,mutation_id,command,create_time,block_numbers.number no,is_done from system.mutations where table='t_user'
```
2、clickhouse 适用于批量大数据量的写入，比如从一个序列化的文件，或者直接从mysql导入











