package cn.soft1010.java.mysql.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by zhangjifu on 2020/12/12.
 */
public class DruidUtil {

    // 创建一个连接池 ds，private对象默认为 null
    private static DataSource ds;

    static {
        try {
            // 1.加载配置文件
            Properties pro = new Properties();
            InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("application-druid.yml");
            pro.load(is);

            // 2.初始化连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取连接池对象
    public static DataSource getDs(){
        return ds;
    }

    // 获取连接对象
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
