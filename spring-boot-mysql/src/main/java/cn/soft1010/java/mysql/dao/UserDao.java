package cn.soft1010.java.mysql.dao;

import cn.soft1010.java.mysql.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangjifu on 2020/12/13.
 */
public interface UserDao {

    User queryUserByName(@Param("name") String name);

}
