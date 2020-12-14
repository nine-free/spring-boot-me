package cn.soft1010.java.springboot.clickhouse.dao;

import cn.soft1010.java.springboot.clickhouse.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangjifu on 2020/12/14.
 */

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
