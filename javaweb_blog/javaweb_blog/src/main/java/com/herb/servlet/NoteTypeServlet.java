package com.herb.servlet;

import com.herb.pojo.NoteType;
import com.herb.pojo.User;
import com.herb.result.ResultInfo;
import com.herb.service.NoteTypeService;
import com.herb.util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/7
 * @Description:
 * @version: 1.0
 */
public class NoteTypeServlet extends HttpServlet {

    private NoteTypeService typeService = new NoteTypeService();


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置导航的高亮
        req.setAttribute("menu_page", "type");

        //接收用户行为
        String actionName = req.getParameter("actionName");

        if ("list".equals(actionName)) {
            //查询类型列表
            typeList(req, resp);
        }else if ("addOrUpdate".equals(actionName)) {
            addOrUpdate(req, resp);
        }else if ("delete".equals(actionName)) {
            deleteType(req, resp);
        }
    }

    /**
     * @Description //TODO 删除类型
     * @param req
     * @param resp
     * @return
     */
    private void deleteType(HttpServletRequest req, HttpServletResponse resp) {
        String typeId = req.getParameter("typeId");

        ResultInfo<NoteType> resultInfo = typeService.deleteType(typeId);

        JsonUtil.toJson(resp, resultInfo);
    }

    /**
     * @Description //TODO 添加或者修改类型
     * @param req
     * @param resp
     * @return
     */
    private void addOrUpdate(HttpServletRequest req, HttpServletResponse resp) {
        //1. 接收参数
        String typeName = req.getParameter("typeName");
        String typeId = req.getParameter("typeId");

        //2. 获取Session作用域
        User user = (User) req.getSession().getAttribute("user");

        //3. 调用service 层
        ResultInfo<Integer> resultInfo =  typeService.addOrUpdate(typeName, user.getUserId(), typeId);

        JsonUtil.toJson(resp, resultInfo);
    }

    /**
     * @Description //TODO 查询类型列表
     * @param req
     * @param resp
     * @return
     */
    private void typeList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 获取Session 作用域
        User user = (User) req.getSession().getAttribute("user");

        //2. 调用 Service 层 的查询方法，查询当前登录用户的类型集合，返回集合
        List<NoteType> typeList = typeService.findTypeList(user.getUserId());

        req.setAttribute("typeList", typeList);

        req.setAttribute("changePage", "type/list.jsp");

        req.getRequestDispatcher(req.getContextPath() + "index.jsp").forward(req, resp);
    }
}
