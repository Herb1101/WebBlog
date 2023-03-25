package com.herb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: herb
 * @Date: 2023/3/1
 * @Description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteType {
    private Integer typeId; // 类型ID
    private String typeName; // 类型名称
    private Integer userId; // 用户ID
}
