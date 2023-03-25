package com.herb.dao;

import com.herb.dao.BaseDao;
import com.herb.pojo.NoteType;
import com.herb.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/7
 * @Description:
 * @version: 1.0
 */
public class NoteTypeDao {

    /**
     * @Description //TODO 通过瀛湖ID查询类型集合
     * @param userId
     * @return
     */
    public List<NoteType> findTypeListByUserId(Integer userId) {
        //1. 定义sql语句
        String sql = "select typeId, typeName, userId from tb_note_type where userId = ?";

        List<Object> params = new ArrayList<>();

        params.add(userId);

        List<NoteType> list = BaseDao.queryRows(sql, params, NoteType.class);

        return list;
    }

    /**
     * @Description //TODO 查询当前用户下， 类型名称是否存在
     * @param typeName 类型名称
     * @param userId 用户id
     * @return
     */
    public Integer checkTypeName(String typeName, Integer userId, String typeId) {
        String sql = "select * from tb_note_type where userId = ? and typeName = ?";

        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);
        params.add(typeName);

        NoteType noteType = (NoteType) BaseDao.queryRow(sql, params, NoteType.class);

        //表示不存，可以添加
        if (noteType == null){
            return 200;
        }else { //存在且 id相同
            //说明需要修改
            if (typeId.equals(noteType.getTypeId().toString())) {
                return 200;
            }
        }
        //存在但是id不同
        return -1;
    }

    /**
     * @Description //TODO 查询当前用户下， 类型名称是否存在 返回主键
     * @param typeName 类型名称
     * @param userId 用户id
     * @return
     */
    public Integer addType(String typeName, Integer userId) {
        Integer key = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnetion();
            String sql = "insert into tb_note_type (typeName,userId) values (?,?)";

            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, typeName);
            preparedStatement.setInt(2, userId);

            int row = preparedStatement.executeUpdate();

            if (row > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    key = resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(resultSet, preparedStatement, connection);
        }
        return key;
    }

    /**
     * @Description //TODO 根据id修改 类型名称
     * @param typeName
     * @param typeId
     * @return
     */
    public Integer updateType(String typeName, String typeId) {
        String sql = "update tb_note_type set typeName =? where typeId = ?";

        ArrayList<Object> params = new ArrayList<>();

        params.add(typeName);
        params.add(typeId);

        int row = BaseDao.executeUpdate(sql, params);
        return row;
    }

    /**
     * @Description //TODO 根据id 删除类型
     * @param typeId
     * @return
     */
    public int deleteTypeById(String typeId) {

        String sql = "delete from tb_note_type where typeId = ?";

        ArrayList<Object> params = new ArrayList<>();
        params.add(typeId);

        int row = BaseDao.executeUpdate(sql, params);

        return row;
    }
}
