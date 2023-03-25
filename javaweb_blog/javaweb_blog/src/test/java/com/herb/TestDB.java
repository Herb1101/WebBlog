package com.herb;

import com.herb.dao.BaseDao;
import com.herb.dao.UserDao;
import com.herb.pojo.User;
import com.herb.util.DBUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
public class TestDB {

    //使用日志工厂类
    private Logger logger = LoggerFactory.getLogger(TestDB.class);

    @Test
    public void testDB() {
        System.out.println(DBUtil.getConnetion());

        //使用日志
        logger.info("获取数据库连接：" + DBUtil.getConnetion());
    }

    @Test
    public void queryUserByName() {
        UserDao userDao = new UserDao();
        User user = userDao.queryUserByName("admin");
        System.out.println(user.getUpwd());
    }

    @Test
    public void testAdd() {
        String sql = "insert into tb_user (uname,upwd,nick,head,mood) values (?,?,?,?,?)";
        List<Object> params = new ArrayList<>();

        params.add("lisi");
        params.add("e10adc3949ba59abbe56e057f20f883e");
        params.add("lisi");
        params.add("404.jpg");
        params.add("Hello");

        int i = BaseDao.executeUpdate(sql, params);

        System.out.println(i);
    }
}
