package com.herb.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author: herb
 * @Date: 2023/3/6
 * @Description:
 * @version: 1.0
 */
public class Index01 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File dir = new File("D:/upload/");
        String[] list = dir.list();
        req.getSession().setAttribute("files", list);
        resp.sendRedirect("upload.jsp");
    }

}
