package com.herb.dao;

import cn.hutool.core.util.StrUtil;
import com.herb.dao.BaseDao;
import com.herb.pojo.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/8
 * @Description:
 * @version: 1.0
 */
public class NoteDao {
    /**
     * @Description //TODO 根据 当前登录用户查询博客数量
     * @param userId
     * @return 
     */
    public long findNoteCount(Integer userId, String title) {
        String sql = "SELECT count(1) FROM tb_note n JOIN " +
                " tb_note_type t " +
                " on n.typeId = t.typeId " +
                " WHERE userId = ?";

        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)) {
            sql += " and title like concat('%', ?, '%')";
            params.add(title);
        }

        long count = (long) BaseDao.findSingleValue(sql, params);

        return count;
    }

    /**
     * @Description //TODO 分页查询 登录用户 当前页面的数据列表
     * @param userId 用户id
     * @param index 分页查询的下标
     * @param pageSize 每页显示数量
     * @return 
     */
    public List<Note> findNoteListByPage(Integer userId, Integer index, Integer pageSize, String title) {
        String sql = "SELECT noteId, title,pubTime FROM tb_note n JOIN " +
                " tb_note_type t " +
                " on n.typeId = t.typeId " +
                " WHERE userId = ? ";

        ArrayList<Object> params = new ArrayList<>();
        params.add(userId);

        if (!StrUtil.isBlank(title)) {
            sql += " and title like concat('%', ?, '%') ";
            params.add(title);
        }

        //拼接分页查询
        sql += " order by pubTime desc limit ?,?";
        params.add(index);
        params.add(pageSize);

        List<Note> noteList = BaseDao.queryRows(sql, params, Note.class);

        return noteList;
    }
}
