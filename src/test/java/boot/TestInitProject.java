package boot;

import com.dwi.saas.generator.ProjectGenerator;
import com.dwi.saas.generator.config.CodeGeneratorConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成 saas-boot项目 新模块
 *
 * @author zuihou
 * @date 2020/05/25
 */
@Slf4j
public class TestInitProject {
	
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
		private static final String PROJECT_DESCRIPTION = "商城";
		private static final String PROJECT_VERSION = "1.0.0-Boot-SNAPSHOT";
		private static final String PORT = "12080";
		private static final String GROUP_ID = "com.foshan.saas";

    public static void main(String[] args) {
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        String path = OUT_PATH;
        config
                // saas-cloud 项目的 绝对路径！
                .setProjectRootPath(path)
                // 项目的前缀
                .setProjectPrefix(PROJECT_PREFIX)
                // 需要新建的 服务名      该例会生成 saas-mall 模块
                .setServiceName(CHILD_MODULE_NAME)
                // 子模块的设置请参考 消息服务 （msg 模块下的 sms 模块即 视为子模块）
//                .setChildModuleName("mall")
                // 子模块的设置请参考 消息服务 （msg 模块下的 sms 模块即 视为子模块）
                .setChildModuleName(CHILD_MODULE_NAME)
                .setIsGenEntity(true)
                .setIsBoot(true)
                // 生成代码的注释 @author dwi
                .setAuthor(AUTHOR)
                // 项目描述
                .setDescription(PROJECT_DESCRIPTION)
                // 项目的版本， 一定要跟 dwi-cloud 下的其他服务版本一致， 否则会出错哦
                .setVersion(PROJECT_VERSION)
                // 服务的端口号
                .setServerPort(PORT)
                // 项目的 groupId
                .setGroupId(GROUP_ID)
        ;
        // 项目的业务代码 存放的包路径
        config.setPackageBase(PACKAGEBASE_PREFIX + config.getChildModuleName());

        System.out.println("项目初始化根路径：" + config.getProjectRootPath());
        ProjectGenerator pg = new ProjectGenerator(config);
        pg.build();
    }
}
