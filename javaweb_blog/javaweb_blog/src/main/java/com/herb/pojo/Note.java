package com.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private Integer noteId; // 博客ID
    private String title; // 博客标题
    private String content; // 博客内容
    private Integer typeId; // 博客类型ID
    private Date pubTime; // 发布时间
    private Float lon; // 经度
    private Float lat; // 纬度


    private String typeName;
}
