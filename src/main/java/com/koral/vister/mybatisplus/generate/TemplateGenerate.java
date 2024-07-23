package com.koral.vister.mybatisplus.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class TemplateGenerate {
    public static void main(String[] args) {
//        FastAutoGenerator.create("jdbc:mysql://10.6.15.15/join_date_temp?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=Asia/Shanghai", "u_temp_data", "zZqCT9$*nEKQ")
          FastAutoGenerator.create("jdbc:oracle:thin:@10.6.4.152:1521:orcl", "test", "test123")
        .globalConfig(builder -> {
                    builder.author("koral") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://templateGenerate//"); // 指定输出目录
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
                    builder.parent("com.join.uc") // 设置父包名
                            //.moduleName("mapper") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\softwares\\gdx\\webvisitercount\\src\\main\\resources\\com\\koral\\vister\\server\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("TB_GOS_VOUCHER_ZDGB_DIVISION_RELATION");// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
