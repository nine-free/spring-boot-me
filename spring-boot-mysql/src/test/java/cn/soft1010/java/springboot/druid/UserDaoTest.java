package cn.soft1010.java.springboot.druid;

import cn.soft1010.java.mysql.ApplicationRunner;
import cn.soft1010.java.mysql.dao.UserDao;
import cn.soft1010.java.mysql.model.User;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhangjifu on 2020/12/13.
 */
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
