## 

##  直接使用jdbc连接mysql数据库
```
    public static void conn(String driverName, String URL, String userName, String password) {
        try {
            // 1.加载驱动程序
            Class.forName(driverName);
            logger.info("加载数据库驱动{}完成", driverName);
            // 2.获得数据库链接
            Connection conn = DriverManager.getConnection(URL, userName, password);
            logger.info("连接数据库{}成功{}", URL, userName);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            String name = "张三";
            //预编译
            String sql = "select * from t_user where name=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                logger.info("查询结果:name:{} age:{}", rs.getString("name"), rs.getInt("age"));
            }
            // 5、关闭资源
            conn.close();
            rs.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
```
##  使用springboot & mybatis & druid 数据库连接池
```

```