package com.herb.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
public class DBUtil {

    private static String driver = null;
    private static String url = null;
    private static String usernmae = null;
    private static String pwd = null;

    //得到配置文件对象
    private static Properties properties = new Properties();

    static {

        try {
            //加载配置文件
            InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
            //通过load（） 方法将输入流 的内容加载到配置文件对象中
            properties.load(inputStream);

            //得到 数据库连接的相关信息
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            usernmae = properties.getProperty("username");
            pwd = properties.getProperty("pwd");
            //通过配置文件对象的 获取 驱动，并加载驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description //TODO 获取数据库连接
     */
    public static Connection getConnetion() {

        Connection connection = null;
        try {
            //连接数据库
            connection = DriverManager.getConnection(url, usernmae, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    /**
     * @Description //TODO 关闭资源
     * @param resultSet
     * @param preparedStatement
     * @param connection
     */
    public static void close(ResultSet resultSet,
                             PreparedStatement preparedStatement,
                             Connection connection) {
        try {
            //判断资源对象是否为空，
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
