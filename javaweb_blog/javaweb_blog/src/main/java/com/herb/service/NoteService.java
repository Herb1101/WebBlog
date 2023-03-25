package com.herb.service;

import cn.hutool.core.util.StrUtil;
import com.herb.dao.NoteDao;
import com.herb.pojo.Note;
import com.herb.util.Page;

import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/8
 * @Description:
 * @version: 1.0
 */
public class NoteService {

    private NoteDao noteDao = new NoteDao();

    /**
     * @Description //TODO 分页查询博客列表
     * @param pageNumStr 当前页
     * @param userId 用户id
     * @return
     */
    public Page<Note> findNoteListByPage(String pageNumStr,String pageSizeStr, Integer userId, String title) {
        //设置分页的默认参数，默认当前页是第一页
        Integer pageNum = 1;
        //默认每页显示 5条数据
        Integer pageSize = 5;

        //1. 参数校验
        if (!StrUtil.isBlank(pageNumStr)){
            //设置当前页
            pageNum = Integer.parseInt(pageNumStr);
        }
        if (!StrUtil.isBlank(pageSizeStr)) {
            //设置 每页的显示数据
            pageSize = Integer.parseInt(pageSizeStr);
        }

        //2. 查询当前登录用户的博客总数量，返回总记录数
        long count = noteDao.findNoteCount(userId, title);

        //3. 判断总记录数是否大于0
        if (count < 1){
            return null;
        }

        //4. 调用 Page 的有参构造器
        Page<Note> page = new Page<Note>(pageNum, pageSize, count);

        //计算分页查询的下标
        Integer index = (pageNum - 1) * pageSize;

        //5. 查询 登录用户当前页的 数据列表，返回 note集合。
        List<Note> noteList = noteDao.findNoteListByPage(userId, index, pageSize, title);

        page.setDataList(noteList);

        return page;
    }
}
