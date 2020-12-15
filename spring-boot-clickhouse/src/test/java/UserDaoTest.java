
import cn.soft1010.java.springboot.clickhouse.SpringBootApplicationStarter;
import cn.soft1010.java.springboot.clickhouse.dao.UserDao;
import cn.soft1010.java.springboot.clickhouse.model.User;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
        User user3 = new User(3, "王五", "2020-10-01");
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
        String[] dates = new String[]{"2020-01-01", "2020-02-01", "2020-03-01", "2020-04-01", "2020-05-01", "2020-06-01",
                "2020-07-01", "2020-08-01", "2020-09-01", "2020-10-01", "2020-11-01", "2020-12-01",
                "2019-01-01", "2019-02-01", "2019-03-01", "2019-04-01", "2019-05-01", "2019-06-01",
                "2019-07-01", "2019-08-01", "2019-09-01", "2019-10-01", "2019-11-01", "2019-12-01"};
        List<User> users = new ArrayList<>();
        int num = 0;
        for (String date : dates) {
            users.add(new User(num++, "name", date));
        }
        long start = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(20, 50, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000000));
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            futures.add(executorService.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    userDao.batchInsertUsers(users);
                    return null;
                }
            }));
        }

        for (Future f : futures) {
            try {
                f.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test3() {
        String[] dates = new String[]{"2020-01-01", "2020-02-01", "2020-03-01", "2020-04-01", "2020-05-01", "2020-06-01",
                "2020-07-01", "2020-08-01", "2020-09-01", "2020-10-01", "2020-12-01",
                "2019-01-01", "2019-02-01", "2019-03-01", "2019-04-01", "2019-05-01", "2019-06-01",
                "2019-07-01", "2019-08-01", "2019-09-01", "2019-10-01", "2019-11-01", "2019-12-01", "2020-11-01"};
        for (String date : dates) {
            userDao.deleteUser(date);
        }
        User user1 = new User(1, "张三", "2020-11-01");
        userDao.saveUser(user1);

    }
}
