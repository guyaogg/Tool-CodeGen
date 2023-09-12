package com.guyaogg.codegenerator.domain.vo;

import lombok.Data;

/**
 * @author guyao
 * @version 1.0
 * @description: 用于接收参数
 * @date 2023/9/11 15:05
 */
@Data
public class CodeGenVO {

    // 类型 0 mongo 类型 ，1 mysql 类型
    private Integer type;
    // 作者姓名
    private String author;
    // do 路径
    private String doPath;
    // query 路径
    private String queryPath;
    // mapper 路径
    private String mapperPath;
    // mapperXml 路径
    private String mapperXmlPath;
    // dao 路径
    private String daoPath;
    // service 路径
    private String servicePath;
    // serviceImpl 路径
    private String serviceImplPath;
    // 已有文件处理方式类型 0 替换， 1 不替换
    private Integer processType;
    // 生产类的主要名称
    private String doName;

}
