package com.herb.dao;

import com.herb.dao.BaseDao;
import com.herb.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
public class UserDao {

    //查询用户
    public User queryUserByName(String userName) {

        //1 定义sql语句
        String sql = "select * from tb_user where uname = ?";

        //2. 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(userName);

        //3. 调用BaseDao的查询方法
        User user = (User) BaseDao.queryRow(sql, params, User.class);

        return user;
    }


    //通过用户id 和昵称查询用户对象
    public User queryUserByNickAndUserId(String nick, Integer userId) {
        //1. 定义sql 语句
        String sql = "select * from tb_user where nick = ? and userId != ?";

        //2. 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(nick);
        params.add(userId);

        //3. 调用BaseDao方法
        User user = (User) BaseDao.queryRow(sql, params, User.class);

        return user;
    }

    //通过用户id 更新用户信息
    public int updateUser(User user) {
        //1. 定义SQL 语句
        String sql = "update tb_user set  nick=?, mood=?, head=? where userId=?";
        //2. 设置参数集合
        ArrayList<Object> params = new ArrayList<>();

        params.add(user.getNick());
        params.add(user.getMood());
        params.add(user.getHead());

        params.add(user.getUserId());

        //3. 调用BaseDao
        int row = BaseDao.executeUpdate(sql, params);

        return row;
    }
}
