package cn.soft1010.java.springboot.clickhouse;

import cn.soft1010.java.springboot.clickhouse.dao.UserDao;
import cn.soft1010.java.springboot.clickhouse.model.User;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Created by zhangjifu on 2020/12/2.
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.soft1010.java.springboot.clickhouse")
public class SpringBootApplicationStarter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplicationStarter.class, args);
        UserDao userDao = (UserDao) SpringUtils.getBean(UserDao.class);
        PageHelper.startPage(2, 1);
        List<User> users = userDao.queryUsersByDate("2020-01-01", "2020-03-01");
        PageInfo pageInfo = new PageInfo(users);
        System.out.println(pageInfo.getTotal());
        System.out.println(JSONObject.toJSON(users));
    }
}
