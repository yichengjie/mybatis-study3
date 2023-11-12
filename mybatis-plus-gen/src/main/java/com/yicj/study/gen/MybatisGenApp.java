package com.yicj.study.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.sql.Types;
import java.util.Collections;

/**
 * @author yicj
 * @date 2023/11/12 13:12
 */
public class MybatisGenApp {

    private static final String rootPath = "D:\\code\\study\\mybatis-study3\\" ;

    public static void main(String[] args) {
        String projectName = "order-service" ;
        String moduleName = "order" ;
        doGenCode(projectName, moduleName);
    }

    private static void doGenCode(String projectName, String moduleName) {
        String url = "jdbc:mysql://127.0.0.1:3306/test" ;
        String username = "root" ;
        String password = "root" ;
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("yicj") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(rootPath + projectName + "\\src\\main\\java\\"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);
                }))
                .packageConfig(builder -> {
                    builder.parent("com.yicj.study") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .mapper("repository.mapper")
                            .entity("repository.entity")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, rootPath + projectName + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .entityBuilder().enableFileOverride()
                            .mapperBuilder().enableFileOverride()
                            .serviceBuilder().enableFileOverride()
                            .controllerBuilder().enableFileOverride()
                    ; // 设置过滤表前缀

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
