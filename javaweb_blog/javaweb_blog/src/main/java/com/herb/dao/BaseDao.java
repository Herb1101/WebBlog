package com.herb.dao;

import com.herb.pojo.User;
import com.herb.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/2
 * @Description: TODO 基础的JDBC操作类 更新（添加、修改、删除）
 *               TODO 查询操作： 1. 查询一个字段
 *                             2. 查询集合
 *                             3. 查询某个对象
 * @version: 1.0
 */
public class BaseDao {


    /**
     * @Description //TODO 更新操作 （添加、修改、删除）
     * @param  sql
     * @param params
     * @return
     */
    public static int executeUpdate (String sql, List<Object> params) {
        //影响行数
        int row = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //1. 获取数据库连接
            connection = DBUtil.getConnetion();

            //2. 预编译
            preparedStatement = connection.prepareStatement(sql);

            //3. 如果有参数，则设置值，从下标1
            if (params != null && params.size() >0) {

                //循环设置参数，
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            //4. 执行更新
            row = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5. 关闭资源
            DBUtil.close(null, preparedStatement, connection);
        }
        return row;
    }

    /**
     * @Description //TODO 查询一个字段  （返回一条记录且只有一个字段，  场景：查询总数量）
     * @param sql
     * @param params
     * @return
     */
    public static Object findSingleValue(String sql, List<Object> params) {
        Object object = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1. 获取数据库连接
            connection = DBUtil.getConnetion();

            //2. 预编译
            preparedStatement = connection.prepareStatement(sql);

            //3. 如果有参数，则设置值，从下标1
            if (params != null && params.size() >0) {

                //循环设置参数，
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }

            //4. 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();

            //5. 判断分析结果
            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //6. 关闭资源
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return object;
    }

    /**
     * @Description //TODO  查询集合 （pojo实体类中 字段要和数据库中的字段 对应）
     * @param sql SQL语句
     * @param params 参数
     * @param cls class对象
     * @return
     */
    public static List queryRows(String sql, List<Object> params, Class cls) {

        List list = new ArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1. 获取数据库连接
            connection = DBUtil.getConnetion();

            //2. 预编译
            preparedStatement = connection.prepareStatement(sql);

            //3. 如果有参数，则设置值，从下标1
            if (params != null && params.size() >0) {

                //循环设置参数，
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }

            //4. 执行查询，返回结果集
            resultSet = preparedStatement.executeQuery();

            //5. 得到结果集的结构信息,表的结构（字段数量，字段名或者列名）
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            //6. 获取 查询的字段数量
            int columnCount = resultSetMetaData.getColumnCount();

            //7. 判断并且分析结构
            while (resultSet.next()) {
                //8.实例化
                Object object = cls.newInstance();
                //9. 遍历查询的 字段数量，得到每一个列名，并且将列名对应的值 塞到实例化的对象中
                for (int i = 1; i <= columnCount; i++) {
                    //通过 表结构 得到 列名
                    //getColumnLabel(): 获得列名 或者 别名
                    //getColumnName(): 获得列名
                    // uname
                    String columnName = resultSetMetaData.getColumnLabel(i);

                    //10. 通过反射，用列名 得到  对象中 相对应的 属性
                    Field field = cls.getDeclaredField(columnName);

                    //uname
                    //columnName.substring(0, 1).toUpperCase() : U
                    //setMethod = setUname
                    //11. 拼接set方法，得到字符串（首字母大写） ,得到 属性的 set 方法名
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

                    //12. 通过反射，获取类中的set方法
                    Method method = cls.getDeclaredMethod(setMethod, field.getType());

                    //13. 查询每一个字段对应的值
                    Object value = resultSet.getObject(columnName);

                    //14. 通过 invoke 调用 set方法
                    method.invoke(object, value);
                }
                //15. 将对象 添加到集合中
                list.add(object);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //16. 关闭资源
            DBUtil.close(resultSet, preparedStatement, connection);
        }

        return list;
    }

    /**
     * @Description //TODO  查询对象
     * @param sql SQL语句
     * @param params 参数
     * @param cls class对象
     * @return
     */
    public static Object queryRow(String sql, List<Object> params, Class cls) {
        List list = queryRows(sql, params, cls);

        Object object = null;
        //如果集合不为空，则或者第一条数据
        if (list != null && list.size() > 0) {
            object = list.get(0);
        }

        return object;
    }
}
