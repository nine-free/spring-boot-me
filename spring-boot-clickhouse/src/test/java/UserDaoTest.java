
import cn.soft1010.java.springboot.clickhouse.SpringBootApplicationStarter;
import cn.soft1010.java.springboot.clickhouse.dao.UserDao;
import cn.soft1010.java.springboot.clickhouse.model.User;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangjifu on 2020/12/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootApplicationStarter.class)
public class UserDaoTest {

    @Resource
    private UserDao userDao;

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

    @Test
    public void test2() {
        PageHelper.startPage(1, 3);
        List<User> users = userDao.queryUsersByDate("2020-01-01", "2020-03-01");
//        Assert.assertEquals(1, users.size());
        System.out.println(JSONObject.toJSON(users));

        System.out.println("=======");
        userDao.batchInsertUsers(users);
    }

    @Test
    public void test3() {
        userDao.deleteUser("2020-10-01");
        userDao.deleteUser("2020-11-01");

    }
}
