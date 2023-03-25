package com.herb.servlet;

import com.herb.pojo.Note;
import com.herb.pojo.User;
import com.herb.service.NoteService;
import com.herb.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: herb
 * @Date: 2023/3/2
 * @Description:
 * @version: 1.0
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置导航 高亮  首页
        req.setAttribute("menu_page", "index");

        String actionName = req.getParameter("actionName");

        //设置用户行为，放到request作用域中
        req.setAttribute("action", actionName);

        if ("searchTitle".equals(actionName)){
            String title = req.getParameter("title");

            //将查询条件 设置到request作用域中
            req.setAttribute("title", title);

            //标题搜索(模糊查询)
            noteList(req, resp, title);
        }else {
            //分页查询博客列表
            noteList(req, resp, null);
        }
        
        //设置 首页动态包含的页面
        req.setAttribute("changePage", "note/list.jsp");

        //请求转发
        req.getRequestDispatcher(req.getContextPath() + "/index.jsp").forward(req, resp);
    }

    /**
     * @Description //TODO 分页查询博客列表----》 总页数，当前页，上一页，下一页。。。。， 查询当前页的数据
     * @param req
     * @param resp
     * @return 
     */
    private void noteList(HttpServletRequest req, HttpServletResponse resp, String title) {
        //1. 接收参数,当前页
        String pageNum = req.getParameter("pageNum");
        String pageSize = req.getParameter("pageSize");

        //2. 获取Session作用域中 user 对象
        User user = (User) req.getSession().getAttribute("user");

        //3.调用sevice层查询方法，返回Page
        Page<Note> page = new NoteService().findNoteListByPage(pageNum, pageSize, user.getUserId(), title);

        //4. 将page 对象设置到 requet作用域中
        req.setAttribute("page", page);
    }



}
