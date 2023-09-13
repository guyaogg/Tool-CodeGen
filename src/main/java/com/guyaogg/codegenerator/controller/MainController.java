package com.guyaogg.codegenerator.controller;

import com.guyaogg.codegenerator.domain.bean.CodeGenData;
import com.guyaogg.codegenerator.domain.vo.CodeGenVO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author guyao
 * @version 1.0
 * @description: 主要操作接口控制台
 * @date 2023/9/11 15:01
 */
@RestController
public class MainController {

    private static final String TEMPLATE_PATH = "src/main/resources/ftl";

    @RequestMapping(value = "/beforeAnalyze")
    public void execute(@RequestBody CodeGenVO codeGenVO) {
        // 1. 检查数据有效性 （ 这个自用就不太做检查了 ）

        // 2. 根据处理类型，如果不替换要先查看是否有文件，有就不需要下面步骤了
        Integer processType = codeGenVO.getProcessType();
        List<CodeGenData> list = new ArrayList<>();
        // 3. 分析组合文件所需数据
        Integer type = codeGenVO.getType();
        String author = codeGenVO.getAuthor();
        String doPath = codeGenVO.getDoPath();
        String mapperPath = codeGenVO.getMapperPath();
        String doName = codeGenVO.getDoName();
        String queryPath = codeGenVO.getQueryPath();
        String servicePath = codeGenVO.getServicePath();
        String serviceImplPath = codeGenVO.getServiceImplPath();
        String daoPath = codeGenVO.getDaoPath();
        String mapperXmlPath = codeGenVO.getMapperXmlPath();
        String doClassName = doName + "DO";
        String mapperClassName = doName + "Mapper";
        String queryClassName = doName + "Query";
        String daoClassName = doName + "DAO";
        String serviceClassName = doName + "Service";
        String serviceImplClassName = doName + "ServiceIml";
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 4. 分类型分类讨论
        String doJavaPath = getJavaPathAndInList(processType, list, author, doPath, doClassName, time, "do", null);
        String queryJavaPath = getJavaPathAndInList(processType, list, author, queryPath, queryClassName, time, "query", null);
        String mapperJavaPath = "";
        switch (type) {
            case 0:
                // mongo 执行
                break;
            case 1:
                // mysql 执行
                mapperJavaPath = getJavaPathAndInList(processType, list, author, mapperPath, mapperClassName, time, "mapper", dataMap -> {
                    dataMap.put("orgClassPath", doJavaPath);
                });
                if (!ObjectUtils.isEmpty(mapperXmlPath)) {
                    boolean canFlag = true;
                    if (processType == 1) {
                        canFlag = getCanFlag(mapperXmlPath, mapperClassName, ".xml");
                    }
                    if (canFlag) {
                        Map<String, Object> dataMap = new HashMap<>();
                        dataMap.put("classPath", mapperClassName);
                        list.add(new CodeGenData(dataMap, mapperClassName, "mapperXml", mapperXmlPath, ".xml"));
                    }
                }
                break;
            default:
                break;
        }
        String finalMapperJavaPath = mapperJavaPath;
        String daoJavaPath = getJavaPathAndInList(processType, list, author, daoPath, daoClassName, time, "dao", dataMap -> {
            dataMap.put("orgClassPath", doJavaPath);
            dataMap.put("queryClassPath", queryJavaPath);
            dataMap.put("orgClassName", doClassName);
            dataMap.put("queryClassName", queryClassName);
            if (type == 0) {
                dataMap.put("queryClassNameXiao", lowFirst(queryClassName));
            }
            if (type == 1) {
                dataMap.put("mapperClassPath", finalMapperJavaPath);
                dataMap.put("mapperClassName", mapperClassName);
                dataMap.put("mapperClassNameXiao", lowFirst(mapperClassName));
            }
        });
        String serviceJavaPath = getJavaPathAndInList(processType, list, author, servicePath, serviceClassName, time, "service", dataMap -> {
            dataMap.put("orgClassPath", doJavaPath);
            dataMap.put("queryClassPath", queryJavaPath);
            dataMap.put("orgClassName", doClassName);
            dataMap.put("queryClassName", queryClassName);
        });

        String serviceImplJavaPath = getJavaPathAndInList(processType, list, author, serviceImplPath, serviceImplClassName, time, "serviceImpl", dataMap -> {
            dataMap.put("orgClassPath", doJavaPath);
            dataMap.put("daoClassPath", daoJavaPath);
            dataMap.put("mapperClassPath", finalMapperJavaPath);
            dataMap.put("serviceClassPath", serviceJavaPath);
            dataMap.put("mapperClassName", mapperClassName);
            dataMap.put("orgClassName", doClassName);
            dataMap.put("serviceClassName", serviceClassName);
            dataMap.put("daoClassName", daoClassName);
            dataMap.put("daoClassNameXiao", lowFirst(daoClassName));
            dataMap.put("orgClassNameXiao", lowFirst(doClassName));
        });

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        // 5. 执行文件生成
        for (CodeGenData codeGenData : list) {
            codeCreated(codeGenData.getMapData(), codeGenData.getCreatedName() + codeGenData.getSuffix(), codeGenData.getTem(), codeGenData.getPath(), type);
        }

    }

    private String getJavaPathAndInList(Integer processType, List<CodeGenData> list, String author, String path, String className, String time, String tem, Consumer<Map<String, Object>> consumer) {
        String javaPath = "";
        if (!ObjectUtils.isEmpty(path)) {
            javaPath = getJavaPath(path) + className;
            boolean canFlag = true;
            if (processType == 1) {
                canFlag = getCanFlag(path, className);
            }
            if (canFlag) {
                if (!ObjectUtils.isEmpty(javaPath)) {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("className", className);
                    dataMap.put("author", author);
                    dataMap.put("time", time);
                    dataMap.put("classPath", javaPath.substring(0, javaPath.lastIndexOf(".")));
                    if (consumer != null) {
                        consumer.accept(dataMap);
                    }
                    list.add(new CodeGenData(dataMap, className, tem, path));
                }
            }
        }
        return javaPath;
    }

    public static void codeCreated(Map<String, Object> dataMap, String createdName, String tem, String path, int type) {
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            // step2 获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH + (type == 0 ? "/mongo" : "/mysql")));
            // step4 加载模版文件
            Template template = configuration.getTemplate(tem + ".ftl");
            // step5 生成数据
            File docFile = new File(path + "\\" + createdName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // step6 输出文件
            template.process(dataMap, out);
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^" + createdName + " 文件创建成功 !");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static boolean getCanFlag(String doPath, String className) {
        return !new File(doPath + "/" + className + ".java").exists();
    }

    private static boolean getCanFlag(String doPath, String className, String fileSuffix) {
        return !new File(doPath + className + fileSuffix).exists();
    }

    public static String lowFirst(String str) {
        if (str == null) return null;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private String getJavaPath(String path) {
        String[] split = path.split("\\\\");
        if (split.length == 1) {
            split = split[0].split("/");
        }
        StringBuilder javaPathBuilder = new StringBuilder();
        boolean javaFlag = false;
        for (String s : split) {
            if (javaFlag) {
                javaPathBuilder.append(s).append(".");
            }
            if (!javaFlag && s.equals("java")) {
                javaFlag = true;
            }
        }
        if (javaFlag) {
            return javaPathBuilder.toString();
        }
        return null;
    }
}
