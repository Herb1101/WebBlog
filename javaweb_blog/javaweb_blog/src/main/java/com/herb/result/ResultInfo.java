package com.herb.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description: TODO 封装返回结果集
 * @version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo<T> {

    //状态码 成功= 200， 失败= -1
    private Integer code;

    //提示信息
    private String msg;

    //返回的对象（字符串，集合，pojo实体类）
    private T result;
}
