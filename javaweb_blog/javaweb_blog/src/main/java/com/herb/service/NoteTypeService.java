package com.herb.service;

import cn.hutool.core.util.StrUtil;
import com.herb.dao.NoteTypeDao;
import com.herb.pojo.NoteType;
import com.herb.result.ResultInfo;

import java.util.List;

/**
 * @author: herb
 * @Date: 2023/3/7
 * @Description:
 * @version: 1.0
 */
public class NoteTypeService {

    private NoteTypeDao typeDao = new NoteTypeDao();

    /**
     * @Description //TODO 查询类型列表
     * @param userId
     * @return
     */
    public List<NoteType> findTypeList(Integer userId) {
        return typeDao.findTypeListByUserId(userId);
    }

    /**
     * @Description //TODO 添加或者修改类型
     * @param typeName 类型名称
     * @param userId 用户id
     * @return
     */
    public ResultInfo<Integer> addOrUpdate(String typeName, Integer userId, String typeId) {

        ResultInfo<Integer> resultInfo = new ResultInfo<>();

        //1. 判空
        if (StrUtil.isBlank(typeName)) {
            resultInfo.setCode(-1);
            resultInfo.setMsg("类型名称不能为空!");
            return resultInfo;
        }

        //2. 调用Dao 层，查询当前用户下， 类型名称是否存在
        Integer code = typeDao.checkTypeName(typeName, userId, typeId);

        if (code == -1) {
            resultInfo.setCode(-1);
            resultInfo.setMsg("类型名称已经存在!");
            return resultInfo;
        }
        
        //3.  添加
        Integer key = null;

        if (StrUtil.isBlank(typeId)) { //需要添加
            key = typeDao.addType(typeName, userId);
        }else {
            key = typeDao.updateType(typeName, typeId);
        }
        if (key > 0) {
            resultInfo.setCode(200);
            resultInfo.setResult(key);
        }else {
            resultInfo.setCode(-1);
            resultInfo.setMsg("更新失败");
        }
        return resultInfo;

    }

    /**
     * @Description //TODO 删除类别
     * @param typeId 类型id
     * @return
     */
    public ResultInfo<NoteType> deleteType(String typeId) {

        ResultInfo<NoteType> resultInfo = new ResultInfo<>();

        if (StrUtil.isBlank(typeId)){
            resultInfo.setCode(-1);
            resultInfo.setMsg("系统异常！");
        }

        int row = typeDao.deleteTypeById(typeId);

        if (row > 0) {
            resultInfo.setCode(200);
        }else {
            resultInfo.setCode(-1);
            resultInfo.setMsg("删除失败");
        }
        return resultInfo;
    }
}
