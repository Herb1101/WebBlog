package com.herb.filter;

import com.herb.pojo.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: herb
 * @Date: 2023/3/2
 * @Description:
 * @version: 1.0
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //基于HTTP
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //得到访问的路径
        //项目路径/资源路径
        String path = request.getRequestURI();

        //1. 指定页面，放行（用户不用登录就能访问的页面， 登陆页面）
        if (path.contains("/login.jsp")) {
            filterChain.doFilter(request, response);
            return;
        }

        //2. 静态资源，放行（存放在static目录下的资源， js，css，images）
        if (path.contains("/statics")) {
            filterChain.doFilter(request, response);
            return;
        }

        //3. 指定行为
        if (path.contains("/user")) {
            //得到用户的行为
            String actionName = request.getParameter("actionName");

            //判断是不是 登录操作
            if("login".equals(actionName)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        //4. 登录状态，放行 （判断session 作用域中 是否存在 user对象，存在就放行，不存在，则拦截跳转到登录页面）
        //获取session作用域中的user对象
        User user = (User)request.getSession().getAttribute("user");

        //判断 user 对象是否为空
        if (user != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO 免登录
        //1. 获取Cookie数组
        Cookie[] cookies = request.getCookies();
        //2. 判断Cookie数组
        if (cookies != null && cookies.length > 0) {
            //3. 遍历Cookie数组，获取指定的Cookie对象
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    //4. 得到对应的cookie对象的value （userName -  userPwd）
                    String value = cookie.getValue();
                    //5. 通过split（）方法将value字符串分割成数组
                    String[] val = value.split("-");

                    //6. 从数组中得到对应的姓名和密码
                    String userName = val[0];
                    String userPwd = val[1];

                    //7. 请求请发到登录操作
                    String url = "user?actionName=login&rem=1&userName=" + userName + "&userPwd="+ userPwd;
                    request.getRequestDispatcher(url).forward(request, response);

                    //8
                    return;
                }
            }
        }

        //拦截，重定向到登录页面
        response.sendRedirect(request.getContextPath() + "login.jsp");
    }

    @Override
    public void destroy() {
    }
}
