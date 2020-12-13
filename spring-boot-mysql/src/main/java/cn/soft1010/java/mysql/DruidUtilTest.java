package cn.soft1010.java.mysql;

import cn.soft1010.java.mysql.druid.DruidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhangjifu on 2020/12/13.
 */
public class DruidUtilTest {

    static final Logger logger = LoggerFactory.getLogger(DruidUtilTest.class);

    public static void main(String[] args) {

        try {
            //获取连接
            Connection connection = DruidUtil.getConnection();
            String name = "张三";
            //预编译
            String sql = "select * from t_user where name=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            // 4.处理数据库的返回结果(使用ResultSet类)
            while (rs.next()) {
                logger.info("查询结果:name:{} age:{}", rs.getString("name"), rs.getInt("age"));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
