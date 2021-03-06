package com.dwi.saas.generator;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
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
public class CodeGenerator {

    public static final String QUERY_PATH = "Query";
    //    public static final String API_PATH = "Api";
    public static final String ENUM_PATH = "Enum";
    public static final String CONSTANT_PATH = "Constant";
    public static final String SAVE_DTO_PATH = "SaveDTO";
    public static final String UPDATE_DTO_PATH = "UpdateDTO";
    public static final String PAGE_DTO_PATH = "PageQuery";

    public static final String SRC_MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java";
    public static final String SRC_MAIN_RESOURCE = "src" + File.separator + "main" + File.separator + "resources";

    public static void run(final CodeGeneratorConfig config) {
        // ไปฃ็ ็ๆๅจ
        AutoGenerator mpg = new AutoGenerator();
        //้กน็ฎ็ๆ น่ทฏๅพ
        String projectRootPath = config.getProjectRootPath();
        //ๅจๅฑ้็ฝฎ
        GlobalConfig gc = globalConfig(config);
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
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();

        System.err.println("----------------------------------------------------------------");
        System.err.println("ไปฃ็ ๅทฒ็ป็ๆๅฎๆฏ๏ผ่ฅๆจ็ๆ็ไปฃ็ ไธๅจcom.dwi.saasๅไธ๏ผ่ฏทๅจnacosไธญ็mysql.yml้็ฝฎๆไปถไธญ่ฐๆดไปฅไธ2ไธชๅๆฐ๏ผ");
        System.err.println("mybatis-plus.typeAliasesPackage");
        System.err.println("mybatis-plus.typeEnumsPackage");
        System.err.println("ๅฆ๏ผtypeAliasesPackage: com.dwi.basic.database.mybatis.typehandler;com.dwi.saas.*.entity;่ฟฝๅ ไฝ ็ๅฎไฝๅ");
        System.err.println("ๅฆ๏ผtypeEnumsPackage: com.dwi.saas.*.enumeration;่ฟฝๅ ไฝ ็ๆไธพๅ");
        System.err.println("----------------------------------------------------------------");
        System.err.println(StrUtil.format("่ฅๆฐๅปบ็ๆๅกๆๆไธพ็ฑปๅ็ๅญๆฎต๏ผ่ฏทๅจsaas-oauth-server/pom.xml ไธญๅ ๅฅ{}{}-entity ๆจกๅ",
                config.getProjectPrefix(), config.getServiceName()));
        System.err.println("ๅนถๅจ OauthGeneralController ็ฑป็'static {  }' ๅคๆทปๅ ๆไธพ็ฑปๅ");
        System.err.println("ๅฆ๏ผ ENUM_MAP.put(ProductType2Enum.class.getSimpleName(), MapHelper.getMap(ProductType2Enum.values()));");
    }

    /**
     * ๅจๅฑ้็ฝฎ
     *
     * @param config      ๅๆฐ
     * @param projectPath ้กน็ฎๆ น่ทฏๅพ
     * @return
     */
    private static GlobalConfig globalConfig(final CodeGeneratorConfig config) {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(String.format("%s/%s", config.getProjectRootPath(), SRC_MAIN_JAVA));
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
        pc.setEntity("domain.entity");
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
        strategy.setSuperServiceClass(pc.getSuperServiceClass());
        strategy.setSuperServiceImplClass(pc.getSuperServiceImplClass());
        strategy.setSuperMapperClass(pc.getSuperMapperClass());
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
                Map<String, Object> map = initImportPackageInfo(config.getPackageBase(), config.getChildPackageName());
                this.setMap(map);
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
    public static Map<String, Object> initImportPackageInfo(String parentPackage, String childPackageName) {
        Map<String, Object> packageMap = new HashMap<>();
        if (childPackageName != null && !"".equals(childPackageName.trim())) {
            childPackageName = "." + childPackageName;
        }

//        packageMap.put(API_PATH, parentPackage + ".api" + childPackageName);
//        packageMap.put(ConstVal.CONTROLLER, parentPackage + ".controller" + childPackageName);

//        packageMap.put(ConstVal.SERVICE, parentPackage + ".service" + childPackageName);
//        packageMap.put(ConstVal.SERVICE_IMPL, parentPackage + ".service" + childPackageName + ".impl");

//        packageMap.put(ConstVal.MAPPER, parentPackage + ".dao" + childPackageName);
        packageMap.put(QUERY_PATH, parentPackage + ".query" + childPackageName);
//        packageMap.put(ConstVal.ENTITY, parentPackage + ".entity" + childPackageName);

        packageMap.put(ENUM_PATH, parentPackage + ".entity" + childPackageName);
        packageMap.put(CONSTANT_PATH, parentPackage + ".constant" + childPackageName);
        packageMap.put("constantSuffix", "Constant");
//        packageMap.put(DTO_PATH, parentPackage + ".dto" + childPackageName);
        packageMap.put(SAVE_DTO_PATH, parentPackage + ".domain.dto" + childPackageName);
        packageMap.put(UPDATE_DTO_PATH, parentPackage + ".domain.dto" + childPackageName);
        packageMap.put(PAGE_DTO_PATH, parentPackage + ".domain.dto" + childPackageName);

        return packageMap;
    }

    private static List<FileOutConfig> getFileConfig(CodeGeneratorConfig config) {
        List<FileOutConfig> focList = new ArrayList<>();

        String projectRootPath = config.getProjectRootPath();
        if (!projectRootPath.endsWith(File.separator)) {
            projectRootPath += File.separator;
        }
        String packageBase = config.getPackageBase().replace(".", File.separator);

        StringBuilder basePathSb = new StringBuilder(projectRootPath).append("%s");
        basePathSb.append(SRC_MAIN_JAVA).append(File.separator)
                .append(packageBase)
                .toString();

        final String basePath = basePathSb.toString();

        focList.add(new FileOutConfigExt(basePath, ConstVal.CONTROLLER, config));
        focList.add(new FileOutConfigExt(basePath, ConstVal.SERVICE, config));
        focList.add(new FileOutConfigExt(basePath, ConstVal.SERVICE_IMPL, config));
        focList.add(new FileOutConfigExt(basePath, ConstVal.MAPPER, config));
        focList.add(new FileOutConfigExt(basePath, ConstVal.XML, config));

        focList.add(new FileOutConfigExt(basePath, QUERY_PATH, config));
        focList.add(new FileOutConfigExt(basePath, CONSTANT_PATH, config));
//        focList.add(new FileOutConfigExt(basePath, DTO_PATH, config));
        final String dtoBasePath = basePathSb.append(File.separator).append("domain").toString();
        focList.add(new FileOutConfigExt(dtoBasePath, SAVE_DTO_PATH, config));
        focList.add(new FileOutConfigExt(dtoBasePath, UPDATE_DTO_PATH, config));
        focList.add(new FileOutConfigExt(dtoBasePath, PAGE_DTO_PATH, config));
        
//        focList.add(new FileOutConfigExt(basePathSb.append(File.separator).append("domain").toString(), SAVE_DTO_PATH, config));
//        focList.add(new FileOutConfigExt(basePathSb.append(File.separator).append("domain").toString(), UPDATE_DTO_PATH, config));
//        focList.add(new FileOutConfigExt(basePathSb.append(File.separator).append("domain").toString(), PAGE_DTO_PATH, config));

        return focList;
    }


}
