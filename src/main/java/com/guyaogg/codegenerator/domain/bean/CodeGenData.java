package com.guyaogg.codegenerator.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author guyao
 * @version 1.0
 * @description: 编码信息汇总信息
 * @date 2023/9/11 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeGenData {
    // 数据
    private Map<String,Object> mapData;
    // 类名称
    private String createdName;
    // 使用模板
    private String tem;
    // 执行路径
    private String path;
    // 类名称后缀
    private String suffix = ".java";
    public CodeGenData(Map<String,Object> mapData,String createdName,String tem, String path) {
        this.mapData = mapData;
        this.createdName = createdName;
        this.tem = tem;
        this.path = path;
    }
}
