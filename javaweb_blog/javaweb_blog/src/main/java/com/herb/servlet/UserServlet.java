package com.herb.servlet;

import com.herb.pojo.User;
import com.herb.result.ResultInfo;
import com.herb.service.UserService;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */

@MultipartConfig
public class UserServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置导航 高亮   个人中心
        req.setAttribute("menu_page", "user");

        //接收用户行为
        String actionName = req.getParameter("actionName");

        //根据用户行为，调用对应方法
        if ("login".equals(actionName)) {
            //用户登录
            userLogin(req, resp);
        }else if ("logout".equals(actionName)) {
            //用户退出
            userLogOut(req, resp);
        }else if("userCenter".equals(actionName)) {
            //进入个人中
            userCenter(req, resp);
        }else if ("userHead".equals(actionName)) {
            //加载头像
            userHead(req, resp);
        }else if("checkNick".equals(actionName)) {
            //验证昵称唯一性
            checkNick(req, resp);
        }else if("updateUser".equals(actionName)) {
            //修改
            updateUser(req, resp);
        }
    }

    /**
     * @Description //TODO 修改用户信息
     * @param req
     * @param resp
     * @return
     */
    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 调用service 层的方法，传递 request对象，返回 resultInfo对象
        ResultInfo<User> resultInfo = userService.updateUser(req);

        //2. 将rusultInfo对象存到 request作用域中
        req.setAttribute("resultInfo", resultInfo);

        //3. 请求转发跳转到个人中心页面
        req.getRequestDispatcher( req.getContextPath() + "/user?actionName=userCenter").forward(req, resp);
    }

    /**
     * @Description //TODO 验证昵称唯一性
     * @param req
     * @param resp
     * @return
     */
    private void checkNick(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 获取昵称
        String nick = req.getParameter("nick");

        //2. 从session作用域中获取用户对象，得到用户id
        User user = (User)req.getSession().getAttribute("user");

        //3. 调用service层，得到返回结果
        Integer code = userService.checkNick(nick, user.getUserId());

        //4. 通过字符输出流将结果响应给前台的ajax的回调函数
        resp.getWriter().write(code + "");
        resp.getWriter().close();
    }

    /**
     * @Description //TODO 加载头像
     * @param req
     * @param resp
     */
    private void userHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 获取参数
        String head = req.getParameter("imageName");

        //2. 得到图片的存放路径
        String realPath = req.getServletContext().getRealPath("/WEB-INF/upload/");

        //3. 通过图片的完整路径，得到file 对象
        File file = new File(realPath + "/" + head);

        //4. 截图图片的后缀
        String suf = head.substring(head.lastIndexOf(".") + 1);

        //5. 通过不同的后缀设置不同的响应类型
        if ("png".equalsIgnoreCase(suf)) {
            resp.setContentType("image/png");
        }else if ("JPG".equalsIgnoreCase(suf) || "JPEG".equalsIgnoreCase(suf)) {
            resp.setContentType("image/jpeg");
        }else if ("GIF".equalsIgnoreCase(suf)) {
            resp.setContentType("image/gif");
        }

        //6. 利用FileUtils的copyFile（）方法，将图片拷贝给浏览器
        FileUtils.copyFile(file, resp.getOutputStream());
    }

    /**
     * @Description //TODO 进入个人中心
     * @param req
     * @param resp
     */
    private void userCenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置 首页动态包含的页面
        req.setAttribute("changePage", "user/info.jsp");

        //请求转发
        req.getRequestDispatcher(req.getContextPath() + "/index.jsp").forward(req, resp);
    }

    /**
     * @Description //TODO 退出
     * @param req
     * @param resp
     */
    private void userLogOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1. 销毁Session
        req.getSession().invalidate();
        //2. 删除cookie对象
        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);//设置0
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    /**
     * @Description //TODO 用户登录
     * @param req
     * @param resp
     * @return
     */
    private void userLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. 获取参数
        String userName = req.getParameter("userName");
        String userPwd = req.getParameter("userPwd");

        //2. 调用 Service层的方法，返回 ResultInfo对象
        ResultInfo<User> resultInfo = userService.userLogin(userName, userPwd);

        //3. 判断是否登录成
        if (resultInfo.getCode() == 200) {

            //将用户信息保存到 session中
            req.getSession().setAttribute("user", resultInfo.getResult());

            //判断用户 有没有选择 记住密码 （rem = 1）
            String rem = req.getParameter("rem");

            //如果是，将用户名和密码保存到 cookie中， 设置失效时间，响应给客户端
            if ("1".equals(rem)) { //选择
                //得到cookie 对象
                Cookie cookie = new Cookie("user", userName + "-" + userPwd);

                //设置失效时间
                cookie.setMaxAge(3*24*60*60);

                //响应客户端
                resp.addCookie(cookie);
            }else { //没有选择
                //清空cookie
                Cookie cookie = new Cookie("user", null);
                //删除cookie
                cookie.setMaxAge(0);
                //响应客户端
                resp.addCookie(cookie);
            }

            //重定向到主页面
            resp.sendRedirect(req.getContextPath() + "index");
        }else {
            //将 resultInfo 对象 设置到 request作用域中
            req.setAttribute(req.getContextPath() + "resultInfo", resultInfo);
            //请求转发跳转到登录页面
            req.getRequestDispatcher(req.getContextPath() + "/login.jsp").forward(req, resp);
        }
    }


}
