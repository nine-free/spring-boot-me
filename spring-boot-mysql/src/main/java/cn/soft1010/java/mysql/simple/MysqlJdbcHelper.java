package cn.soft1010.java.mysql.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Java中使用JDBC连接数据库
 * 1） 加载驱动
 * 2） 创建数据库连接
 * 3） 创建执行sql的语句
 * 4） 执行语句
 * 5） 处理执行结果
 * 6） 释放资源
 *
 * @author liu.hb
 */
public class MysqlJdbcHelper {

    static final Logger logger = LoggerFactory.getLogger(MysqlJdbcHelper.class);

    /**
     * Statement 和 PreparedStatement之间的关系和区别.
     * 关系：PreparedStatement继承自Statement,都是接口
     * 区别：PreparedStatement可以使用占位符，是预编译的，批处理比Statement效率高
     */
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


    public static void main(String[] args) {
        String URL = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&";
        String USER = "root";
        String PASSWORD = "zhang1019";
        String driverName = "com.mysql.cj.jdbc.Driver";

        conn(driverName, URL, USER, PASSWORD);
    }
}