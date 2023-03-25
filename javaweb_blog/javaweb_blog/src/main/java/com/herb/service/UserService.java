package com.herb.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.herb.dao.UserDao;
import com.herb.pojo.User;
import com.herb.result.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
public class UserService {

    private UserDao userDao = new UserDao();


    /**
     * @Description //TODO 用户登录
     * @param userName 用户名
     * @param userPwd 密码
     * @return
     */
    public ResultInfo<User> userLogin(String userName, String userPwd) {

        ResultInfo<User> resultInfo = new ResultInfo<>();

        //数据回显,当登录失败的时候，将登陆信息返回给页面显示
        User userData = new User();
        userData.setUname(userName);
        userData.setUpwd(userPwd);
        resultInfo.setResult(userData);

        //1. 判断参数是否为空
        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)) {
            //如果为空， 设置ResultInfo 对象的状态码信息和提示信息
            resultInfo.setCode(-1);
            resultInfo.setMsg("用户名或密码不能为空！");
            //返回 ResultInfo 对象
            return resultInfo;
        }

        //2. 如果不为空，通过 用户名 查询 对象
        User user = userDao.queryUserByName(userName);

        if (user == null) {
            //如果为空， 设置ResultInfo 对象的状态码信息和提示信息
            resultInfo.setCode(-1);
            resultInfo.setMsg("用户不存在！");
            //返回 ResultInfo 对象
            return resultInfo;
        }

        //如果不为空，将数据库中查询到的用户对象的密码 和  前端 填写的 密码作比较
        // 将前台 输入的密码进行加密
        userPwd = DigestUtil.md5Hex(userPwd);

        //判断加密后的密码和数据中中的是否一致
        if (!userPwd.equals(user.getUpwd())) {
            //如果密码不正确， 设置ResultInfo 对象的状态码信息和提示信息
            resultInfo.setCode(-1);
            resultInfo.setMsg("用户密码不正确");
            //返回 ResultInfo 对象
            return resultInfo;
        }

        resultInfo.setCode(200);
        resultInfo.setResult(user);
        return resultInfo;
    }


    /**
     * @Description //TODO 验证昵称唯一性
     * @param nick 昵称
     * @param userId 用户id
     * @return
     */
    public Integer checkNick(String nick, Integer userId) {
        //1. 判断昵称是否为空
        if (StrUtil.isBlank(nick)) {
            return 0;
        }

        //2. 调用Dao层：通过用户id 和昵称查询用户对象
        User user = userDao.queryUserByNickAndUserId(nick, userId);

        //不为空，user存在 ===> 说明昵称 存在
        if (user != null) {
            return 0;
        }

        //user不存在 ==>说明昵称 不存在（唯一）
        return 200;
    }


    /**
     * @Description //TODO
     * @param req
     * @return
     */
    public ResultInfo<User> updateUser(HttpServletRequest req) {
        ResultInfo<User> resultInfo = new ResultInfo<>();

        //1. 获取参数
        String nick = req.getParameter("nick");
        String mood = req.getParameter("mood");

        //2. 参数校验
        if (StrUtil.isBlank(nick)) {
            //如果为空，将状态码和错误信息设置 到 rusultInfo对象中，返回rusultInfo对象
            resultInfo.setCode(-1);
            resultInfo.setMsg("用户昵称不能为空！");
            return resultInfo;
        }

        //3. 从session 作用域中获取对象
        User user = (User) req.getSession().getAttribute("user");
        //设置昵称和心情
        user.setNick(nick);
        user.setMood(mood);

        //4. 实现文件上传
        try {
            //1.获取上传问文件
            Part part = req.getPart("img");
            //2.从头部信息拿到上传的文件名
            String fileName = part.getSubmittedFileName();

            //3. 判断文件名是否为空
            if (! StrUtil.isBlank(fileName)) {
                //设置 用户的头像名称
                user.setHead(fileName);

                //4. 获取文件存放路径  WEB-INF/upload/目录中
                String filePath = req.getServletContext().getRealPath("/WEB-INF/upload/");

                //5. 上传文件到指定目录
                part.write(filePath + "/" + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //6. 调用Dao层的更新方法，返回影响行数
        int row = userDao.updateUser(user);

        if (row > 0) {
            resultInfo.setCode(200);

            //更新session中的用户对象
            req.getSession().setAttribute("user", user);
        }else {
            resultInfo.setCode(-1);
            resultInfo.setMsg("更新失败！");
        }
        return resultInfo;
    }
}
