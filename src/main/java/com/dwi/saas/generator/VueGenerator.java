package com.dwi.saas.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.DB2Query;
import com.baomidou.mybatisplus.generator.config.querys.DMQuery;
import com.baomidou.mybatisplus.generator.config.querys.H2Query;
import com.baomidou.mybatisplus.generator.config.querys.MariadbQuery;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.querys.SqlServerQuery;
import com.baomidou.mybatisplus.generator.config.querys.SqliteQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.dwi.saas.generator.config.CodeGeneratorConfig;
import com.dwi.saas.generator.ext.FileOutConfigExt;
import com.dwi.saas.generator.ext.FreemarkerTemplateEngineExt;
import com.dwi.saas.generator.ext.MySqlQueryExt;
import com.dwi.saas.generator.ext.OracleQueryExt;
import com.dwi.saas.generator.type.GenerateType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ไปฃ็ ็ๆ
 *
 * @author dwi
 * @date 2020/05/25
 */
public class VueGenerator {

    public static final String API_PATH = "Api";
    public static final String PAGE_INDEX_PATH = "PageIndex";
    public static final String TREE_INDEX_PATH = "TreeIndex";
    public static final String EDIT_PATH = "Edit";
    public static final String LANG_PATH = "Lang";

    public static final String SRC = "src";

    public static void run(final CodeGeneratorConfig config) {
        // ไปฃ็ ็ๆๅจ
        AutoGenerator mpg = new AutoGenerator();
        //้กน็ฎ็ๆ น่ทฏๅพ
        String projectRootPath = config.getProjectRootPath();

        //ๅจๅฑ้็ฝฎ
        GlobalConfig gc = globalConfig(config, projectRootPath);
        mpg.setGlobalConfig(gc);

        // ๆฐๆฎๆบ้็ฝฎ
        DataSourceConfig dsc = dataSourceConfig(config);
        mpg.setDataSource(dsc);

        PackageConfig pc = packageConfig(config);
        mpg.setPackageInfo(pc);

        // ้็ฝฎๆจกๆฟ
        TemplateConfig templateConfig = new TemplateConfig();
        // ไธ็ๆไธๅๆไปถ
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setService(null);
        templateConfig.setMapper(null);
        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        mpg.setTemplate(templateConfig);

        // ่ชๅฎไน้็ฝฎ
        InjectionConfig cfg = injectionConfig(config, projectRootPath, pc);
        mpg.setCfg(cfg);

        // ็ญ็ฅ้็ฝฎ
        StrategyConfig strategy = strategyConfig(config);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngineExt(config));

        mpg.execute();


        System.err.println("ๅ็ซฏไปฃ็ ็ๆๅฎๆฏ๏ผ ่ฏทๅจไปฅไธๆฅๅฟไธญๆฅ็็ๆๆไปถ็่ทฏๅพ");
        System.err.println("ๅนถๅฐsrc/lang/lang.*.jsไธญ็้็ฝฎๆ็งๆไปถๆ็คบ๏ผๅคๅถๅฐen.jsๅzh.js, ๅฆๅ้กต้ขๆ ๆณๆพ็คบไธญๆๆ ้ข");
    }

    /**
     * ๅจๅฑ้็ฝฎ
     *
     * @param config      ๅๆฐ
     * @param projectPath ้กน็ฎๆ น่ทฏๅพ
     * @return
     */
    private static GlobalConfig globalConfig(final CodeGeneratorConfig config, String projectPath) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(String.format("%s/%s", projectPath, SRC));
        gc.setAuthor(config.getAuthor());
        gc.setOpen(false);
        gc.setServiceName("%sService");
        gc.setFileOverride(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setDateType(DateType.TIME_PACK);
        gc.setIdType(IdType.INPUT);
        // ๅฎไฝๅฑๆง Swagger2 ๆณจ่งฃ
        gc.setSwagger2(true);
        return gc;
    }

    /**
     * ๆฐๆฎๅบ่ฎพ็ฝฎ
     *
     * @param config
     * @return
     */
    private static DataSourceConfig dataSourceConfig(CodeGeneratorConfig config) {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(config.getUrl());
        dsc.setDriverName(config.getDriverName());
        dsc.setUsername(config.getUsername());
        dsc.setPassword(config.getPassword());
        if (dsc.getDbType() == DbType.MYSQL) {
            dsc.setDbQuery(new MySqlQueryExt());
        }
        // oracle ๆฒกๅฎๅจๆต่ฏ
        else if (dsc.getDbType() == DbType.ORACLE) {
            dsc.setDbQuery(new OracleQueryExt());
        }
        // ไปฅไธ็้ฝๆฒกๆต่ฏ่ฟ
        else if (dsc.getDbType() == DbType.DB2) {
            dsc.setDbQuery(new DB2Query());
        } else if (dsc.getDbType() == DbType.DM) {
            dsc.setDbQuery(new DMQuery());
        } else if (dsc.getDbType() == DbType.H2) {
            dsc.setDbQuery(new H2Query());
        } else if (dsc.getDbType() == DbType.MARIADB) {
            dsc.setDbQuery(new MariadbQuery());
        } else if (dsc.getDbType() == DbType.POSTGRE_SQL) {
            dsc.setDbQuery(new PostgreSqlQuery());
        } else if (dsc.getDbType() == DbType.SQLITE) {
            dsc.setDbQuery(new SqliteQuery());
        } else if (dsc.getDbType() == DbType.SQL_SERVER) {
            dsc.setDbQuery(new SqlServerQuery());
        }
        return dsc;
    }


    private static PackageConfig packageConfig(final CodeGeneratorConfig config) {
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(config.getChildPackageName());
        pc.setParent(config.getPackageBase());
        pc.setMapper("dao");
        if (StringUtils.isNotBlank(config.getChildPackageName())) {
            pc.setMapper(pc.getMapper() + StringPool.DOT + config.getChildPackageName());
            pc.setEntity(pc.getEntity() + StringPool.DOT + config.getChildPackageName());
            pc.setService(pc.getService() + StringPool.DOT + config.getChildPackageName());
            pc.setServiceImpl(pc.getService() + StringPool.DOT + "impl");
            pc.setController(pc.getController() + StringPool.DOT + config.getChildPackageName());
        }
//        pc.setPathInfo(pathInfo(config));
        return pc;
    }

    private static StrategyConfig strategyConfig(CodeGeneratorConfig pc) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setInclude(pc.getTableInclude());
        strategy.setExclude(pc.getTableExclude());
        strategy.setLikeTable(pc.getLikeTable());
        strategy.setNotLikeTable(pc.getNotLikeTable());
        strategy.setTablePrefix(pc.getTablePrefix());
        strategy.setFieldPrefix(pc.getFieldPrefix());
        strategy.setEntityColumnConstant(GenerateType.IGNORE.neq(pc.getFileCreateConfig().getGenerateConstant()));
        strategy.setRestControllerStyle(true);
        strategy.setSuperEntityClass(pc.getSuperEntity().getVal());
        strategy.setSuperControllerClass(pc.getSuperControllerClass());

        strategy.setSuperEntityColumns(pc.getSuperEntity().getColumns());
        return strategy;
    }

    /**
     * InjectionConfig   ่ชๅฎไน้็ฝฎ   ่ฟ้ๅฏไปฅ่ฟ่กๅ่ทฏๅพ็้็ฝฎ๏ผ่ชๅฎไน็ไปฃ็ ็ๆๅจ็ๆฅๅฅ้็ฝฎใ่ฟ้ๅฎไนไบxmlmapper ๅqueryไธคไธชๆไปถ็่ชๅจ็ๆ้็ฝฎ
     */
    private static InjectionConfig injectionConfig(final CodeGeneratorConfig config, String projectRootPath, PackageConfig pc) {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = initImportPackageInfo(config);
                this.setMap(map);
            }

            @Override
            public void initTableMap(TableInfo tableInfo) {
                this.initMap();
            }
        };
        cfg.setFileCreate(config.getFileCreateConfig());

        // ่ชๅฎไน่พๅบ้็ฝฎ
        cfg.setFileOutConfigList(getFileConfig(config));
        return cfg;
    }

    /**
     * ้็ฝฎๅไฟกๆฏ    ้็ฝฎ่งๅๆฏ๏ผ   parentPackage + "ๅฑ" + "ๆจกๅ"
     */
    public static Map<String, Object> initImportPackageInfo(CodeGeneratorConfig config) {
        String parentPackage = config.getPackageBase();
        String childPackageName = config.getChildPackageName();
        Map<String, Object> packageMap = new HashMap<>();

        packageMap.put("serviceName", config.getServiceName());
        packageMap.put("childPackageName", childPackageName);

        return packageMap;
    }

    private static List<FileOutConfig> getFileConfig(CodeGeneratorConfig config) {
        List<FileOutConfig> focList = new ArrayList<>();

        String projectRootPath = config.getProjectRootPath();
        if (!projectRootPath.endsWith(File.separator)) {
            projectRootPath += File.separator;
        }

        StringBuilder basePathSb = new StringBuilder(projectRootPath).append(SRC);

        final String basePath = basePathSb.toString();

        focList.add(new FileOutConfigExt(basePath, API_PATH, config));
        focList.add(new FileOutConfigExt(basePath, PAGE_INDEX_PATH, config));
        focList.add(new FileOutConfigExt(basePath, EDIT_PATH, config));
        focList.add(new FileOutConfigExt(basePath, LANG_PATH, config));
        focList.add(new FileOutConfigExt(basePath, TREE_INDEX_PATH, config));

        return focList;
    }


}
