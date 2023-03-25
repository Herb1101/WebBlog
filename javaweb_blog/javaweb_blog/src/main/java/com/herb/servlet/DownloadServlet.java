package com.herb.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author: herb
 * @Date: 2023/3/6
 * @Description:
 * @version: 1.0
 */
@MultipartConfig
public class DownloadServlet extends HttpServlet {
    String path = "d:/upload/";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //要下载的文件名
        String file = req.getParameter("file");

        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(path + file);

        ServletOutputStream outputStream = resp.getOutputStream();

        //将文件名转码
        String fileName = new String(file.getBytes(), "iso-8859-1");
        //告诉浏览器这是一个二进制文件
        resp.setContentType("application/octet-stream");
        //告诉浏浏览器文件名
        resp.setHeader("Content-Disposition","attachment; filename=" + fileName);

        //告诉浏览器文件大小是多少
        resp.setContentLength(fileInputStream.available());

        byte[] data = new byte[1024];
        int len;

        while ((len = fileInputStream.read(data)) != -1) {
            outputStream.write(data, 0, len);
        }
        fileInputStream.close();
        outputStream.close();

    }
}
