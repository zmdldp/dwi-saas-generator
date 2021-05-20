package boot;

import com.dwi.saas.generator.CodeGenerator;
import com.dwi.saas.generator.config.CodeGeneratorConfig;
import com.dwi.saas.generator.config.FileCreateConfig;
import com.dwi.saas.generator.type.EntityFiledType;
import com.dwi.saas.generator.type.EntityType;
import com.dwi.saas.generator.type.GenerateType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 生成saas-boot项目的代码
 *
 * @author dwi
 * @date 2020/05/25
 */
public class TestCodeGenerator {
	
	//数据库用户名
	private static final String USERNAME = "root";
	//数据库密码
	private static final String PASSWORD = "root";
	//数据库连接
	private static final String DB_URL = "jdbc:mysql://192.168.1.83:3306/foshan-devtest?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
	//是否Boot项目
	private static final Boolean ISBOOT = true;
	//输出路径
	private static final String OUT_PATH = "E:\\generator";
	//项目名前缀
	private static final String PROJECT_PREFIX = "foshan-";
	//包名前缀
	private static final String PACKAGEBASE_PREFIX = "com.foshan.saas.";
	//表名
	private static final String[] TABLE_ARRAY= new String[] {"b_product"};	
	//serviceName 服务名 eg： msgschildModuleName 子模块名 eg: sms、emial
	private static final String SERVICE_NAME = "mall";
	private static final String CHILD_MODULE_NAME = "product";	
	private static final String AUTHOR = "dwi";
	private static final String TABLE_PREFIX = "b_";
		
    public static CodeGeneratorConfig buildMallEntity() {
        List<String> tables = Arrays.asList(TABLE_ARRAY);
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build(SERVICE_NAME, CHILD_MODULE_NAME, AUTHOR, TABLE_PREFIX, tables);
        build.setSuperEntity(EntityType.ENTITY);
        //build.setChildPackageName("ces");
        build.setUrl(DB_URL);
        build.setIsGenEntity(true);     
        return build;
    }
	
    public static void main(String[] args) {
        CodeGeneratorConfig build = buildMallEntity();
        //       CodeGeneratorConfig build = buildManEntity();

        //mysql 账号密码
        build.setUsername(USERNAME);
        build.setPassword(PASSWORD);
        // 指定是boot项目
        build.setIsBoot(ISBOOT);
        String path = OUT_PATH;
        System.out.println("输出路径：" + path);
        build.setProjectRootPath(path);
        // 项目前缀
        build.setProjectPrefix(PROJECT_PREFIX);
        // 指定全部代码的生成策略
        GenerateType generate = GenerateType.OVERRIDE;
//        generate = null;
        // 构造器传入null，下面设置的策略（setGenerate*）才会生效， 构造器传入不为null时，下面设置的策略（setGenerate*）无效，将全部使用构造器传入的策略
        FileCreateConfig fileCreateConfig = new FileCreateConfig(generate);
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.OVERRIDE);
        // 覆盖生成Xml
        fileCreateConfig.setGenerateXml(GenerateType.OVERRIDE);
        // 忽略生成Dao
        fileCreateConfig.setGenerateDao(GenerateType.IGNORE);
        fileCreateConfig.setGenerateServiceImpl(GenerateType.IGNORE);
        fileCreateConfig.setGenerateService(GenerateType.IGNORE);
        fileCreateConfig.setGenerateController(GenerateType.IGNORE);
        build.setFileCreateConfig(fileCreateConfig);

        //手动指定枚举类 生成的路径
        Set<EntityFiledType> filedTypes = new HashSet<>();
        filedTypes.addAll(Arrays.asList(
//                EntityFiledType.builder().name("httpMethod").table("c_common_opt_log")
//                        .packagePath("com.dwi.saas.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
        ));
        build.setFiledTypes(filedTypes);
        build.setPackageBase(PACKAGEBASE_PREFIX + build.getChildModuleName());
        // 运行
        CodeGenerator.run(build);
    }



    public static CodeGeneratorConfig buildManEntity() {
        List<String> tables = Arrays.asList(
                "b_order"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("mall", "order", "dwi", "b_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/foshan-devtest?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");

        build.setIsGenEntity(true);
        return build;
    }

}
