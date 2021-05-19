package cloud.biz;

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
 * 测试代码生成权限系统的代码
 *
 * @author dwi
 * @date 2020/05/25
 */
public class TestAuthorityCodeGenerator {
    /***
     * 注意，想要在这里直接运行，需要手动增加 mysql 驱动
     * @param args
     */
    public static void main(String[] args) {
//        CodeGeneratorConfig build = buildDefaultsEntity();
//        CodeGeneratorConfig build = buildAuthSuperEntity();
        CodeGeneratorConfig build = buildAuthEntity();
//        CodeGeneratorConfig build = buildCommonEntity();
//        CodeGeneratorConfig build = buildCommonSuperEntity();
//        CodeGeneratorConfig build = buildCoreEntity();
//        CodeGeneratorConfig build = buildOrderEntity();
        build.setUsername("root");
        build.setPassword("root");

        String path = "/generator/saas-cloud-plus/saas-authority";
        System.err.println("输出路径：" + path);
        build.setProjectRootPath(path);

        // null 表示 使用下面的 生成策略
        FileCreateConfig fileCreateConfig = new FileCreateConfig(null);
        // 不为null 表示忽略下面的 生成策略
//        FileCreateConfig fileCreateConfig = new FileCreateConfig(GenerateType.OVERRIDE);

        //实体类的生成策略 为覆盖
        fileCreateConfig.setGenerateEntity(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateEnum(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateDto(GenerateType.OVERRIDE);
        fileCreateConfig.setGenerateXml(GenerateType.OVERRIDE);
        //dao 的生成策略为 忽略
        fileCreateConfig.setGenerateDao(GenerateType.IGNORE);
        fileCreateConfig.setGenerateServiceImpl(GenerateType.IGNORE);
        fileCreateConfig.setGenerateService(GenerateType.IGNORE);
        fileCreateConfig.setGenerateController(GenerateType.IGNORE);
        build.setFileCreateConfig(fileCreateConfig);

        //手动指定枚举类 生成的路径
        Set<EntityFiledType> filedTypes = new HashSet<>();
        filedTypes.addAll(Arrays.asList(
                EntityFiledType.builder().name("httpMethod").table("c_opt_log")
                        .packagePath("com.dwi.saas.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
                , EntityFiledType.builder().name("httpMethod").table("c_resource")
                        .packagePath("com.dwi.saas.common.enums.HttpMethod").gen(GenerateType.IGNORE).build()
                , EntityFiledType.builder().name("dsType").table("c_role")
                        .packagePath("com.dwi.basic.database.mybatis.auth.DataScopeType").gen(GenerateType.IGNORE).build()
        ));
        build.setFiledTypes(filedTypes);
        CodeGenerator.run(build);
    }

    public static CodeGeneratorConfig buildDefaultsEntity() {
        List<String> tables = Arrays.asList(
                "d_global_user",
                "d_tenant"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "d_", tables);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("defaults");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_defaults?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    private static CodeGeneratorConfig buildAuthEntity() {
        List<String> tables = Arrays.asList(
//                "c_user_token"
//                , "c_application"
//                , "c_resource"
//                , "c_role"
//                 "c_user"
                "c_menu"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "c_", tables);
//                build("authority", "", "dwi", "c_", tables);
//        build.setSuperEntity(EntityType.ENTITY);
        build.setSuperEntity(EntityType.TREE_ENTITY);
        build.setChildPackageName("auth");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildAuthSuperEntity() {
        List<String> tables = Arrays.asList(
                "c_role_authority"
                , "c_role_org"
                , "c_user_role"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "c_", tables);
        build.setSuperEntity(EntityType.SUPER_ENTITY);
        build.setChildPackageName("auth");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    private static CodeGeneratorConfig buildCommonEntity() {
        List<String> tables = Arrays.asList(
//                "c_area"
//                "c_parameter"
                 "c_dictionary"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "c_", tables);

//        build.setSuperEntity(EntityType.TREE_ENTITY);
        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("common");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildCommonSuperEntity() {
        List<String> tables = Arrays.asList(
                "c_opt_log", "c_opt_log_ext", "c_login_log"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "c_", tables);
        build.setSuperEntity(EntityType.SUPER_ENTITY);
        build.setChildPackageName("common");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }

    public static CodeGeneratorConfig buildCoreEntity() {
        List<String> tables = Arrays.asList(
                "c_org"
//                "c_station"
        );
        CodeGeneratorConfig build = CodeGeneratorConfig.
                build("authority", "", "dwi", "c_", tables);
        build.setSuperEntity(EntityType.TREE_ENTITY);
//        build.setSuperEntity(EntityType.ENTITY);
        build.setChildPackageName("core");
        build.setUrl("jdbc:mysql://192.168.1.83:3306/saas_base_0000?serverTimezone=CTT&characterEncoding=utf8&useUnicode=true&useSSL=false&autoReconnect=true&zeroDateTimeBehavior=convertToNull");
        return build;
    }
}
