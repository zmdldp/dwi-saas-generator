package cloud;

import com.dwi.saas.generator.ProjectGenerator;
import com.dwi.saas.generator.config.CodeGeneratorConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 生成saas-cloud项目的新服务或新模块
 *
 * @author zuihou
 * @date 2020/05/25
 */
@Slf4j
public class TestInitProject {

    public static void main(String[] args) {
        CodeGeneratorConfig config = new CodeGeneratorConfig();
        String path = "/Users/dwi/github/saas-cloud";
        config
                // saas-cloud 项目的 绝对路径！ 路径只能到saas-cloud级
                .setProjectRootPath(path)
                // saas-cloud 项目的前缀 若你的项目修改成了其他，则需要通过这里改前缀
                .setProjectPrefix("saas-")

                // 需要新建的 服务名      该例会生成 saas-test 服务
                .setServiceName("test")

                // 首次新建服务时，设置为空字符串
                // 然后想新建子模块时，可以设置成子模块名  如：msg 服务下的 sms 模块即 视为子模块
                .setChildModuleName("man")

                // 子模块是否需要生成entity模块
                .setIsGenEntity(false)
                // 是否saas-boot项目
                .setIsBoot(false)

                // 生成代码的开发人员Git账号
                .setAuthor("zuihou")
                // 项目描述
                .setDescription("商城")
                // 项目的版本， 一定要跟 saas-cloud 下的其他服务版本一致， 否则会出错哦
                .setVersion("3.0.0-SNAPSHOT")
                // 服务的端口号
                .setServerPort("12080")
                // 项目的 groupId
                .setGroupId("com.dwi.saas")
        ;
        // 项目的业务代码 存放的包路径
        config.setPackageBase("com.dwi.saas." + config.getChildModuleName());

        System.out.println("项目初始化根路径：" + config.getProjectRootPath());
        ProjectGenerator pg = new ProjectGenerator(config);
        pg.build();
    }
}
