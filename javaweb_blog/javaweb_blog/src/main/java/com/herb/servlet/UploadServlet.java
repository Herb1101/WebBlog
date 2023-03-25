package com.herb.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: herb
 * @Date: 2023/3/6
 * @Description:
 * @version: 1.0
 */
@MultipartConfig
public class UploadServlet extends HttpServlet {

    String path = "d:/upload/";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part file = req.getPart("file");

        //获取文件名
        String fileName = file.getSubmittedFileName();

        //获取文件的输入流
        InputStream inputStream = file.getInputStream();

        //获取的文件 传到 path路径
        FileOutputStream fileOutputStream = new FileOutputStream(path + fileName);

        byte[] data = new byte[1024];
        int len;

        while ((len = inputStream.read(data)) != -1) {
            fileOutputStream.write(data, 0, len);
        }
        inputStream.close();
        fileOutputStream.close();
    }
}
