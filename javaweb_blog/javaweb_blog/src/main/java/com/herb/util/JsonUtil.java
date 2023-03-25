package com.herb.util;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: herb
 * @Date: 2023/3/7
 * @Description: TODO: 将对象转换为 JSON字符串。响应给 ajax回调
 * @version: 1.0
 */
public class JsonUtil {

    /**
     * @Description //TODO 将对象转换成JSON
     * @param response
     * @param result 需要转换的对象
     * @return
     */
    public static void toJson(HttpServletResponse response,  Object result) {


        try {
            //设置响应类型
            response.setContentType("appliaction/json;charset=UTF-8");
            //得到字符输出流
            PrintWriter writer = response.getWriter();

            //通过fastJSon方法， 将result对象转换成JSON字符串
            String json = JSON.toJSONString(result);
            writer.write(json);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
