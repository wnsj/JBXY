package com.jiubo.project.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @desc:mybatis-plus生成Action，service,dao,bean
 * @date: 2019-08-02 09:28
 * @author: dx
 * @version: 1.0
 */
public class CodeGenerator {
    public static void main(String[] args) {
        // 包名+模块
        String packageName = "com.jiubo.project";
        boolean serviceNameStartWithI = false;//auth -> UserService, 设置成true: auth -> IUserService
        //把需要自动生成的表 放在这里!!
        generateByTables(serviceNameStartWithI, packageName, "swd", "boot", "password");
        System.out.println("completed...");
    }

    /* *
     * @desc:
     * @author: dx
     * @date: 2019-08-02 11:26:39
     * @param serviceNameStartWithI :service接口是否加I
     * @param packageName :包名
     * @param author :作者
     * @param database :数据库名
     * @param tableNames :表名
     * @return: void
     * @throws:
     * @version: 1.0
     **/
    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String author, String database, String... tableNames) {
        String filePath = "src/main/java/";
        String dbUrl = "jdbc:mysql://172.16.6.78:3306/jbxy?useSSL=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useTimezone=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("root")
                .setPassword("root")
                .setDriverName("com.mysql.cj.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                //.setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
//              .setSuperMapperClass("cn.saytime.mapper.BaseMapper")
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        GlobalConfig config = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        config.setActiveRecord(false)
                .setOutputDir(projectPath + "/src/main/java")
                .setAuthor(author)
                .setFileOverride(true)
                .setEntityName("%sBean")
                .setMapperName("%sDao")
                .setXmlName("%sMapper")
                .setSwagger2(true)
                .setEnableCache(false);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }

        PackageConfig pc = new PackageConfig()
                .setParent(packageName)
                .setController("action")
                .setEntity("bean")
                .setMapper("dao")
                .setService("service")
                .setServiceImpl("service.impl")
                .setXml("mapper");

        // 配置模板
        //TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        //templateConfig.setXml(null);
        new AutoGenerator()
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(pc)
                .setCfg(cfg)
                //.setTemplate(templateConfig)
                .execute();
    }


}