package ${packageBase}.config;

import com.dwi.saas.oauth.api.LogApi;
import com.dwi.basic.boot.config.BaseConfig;
import com.dwi.basic.log.event.SysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ${description}-Web配置
 *
 * @author ${author}
 * @date ${date}
 */
@Configuration
public class ${service}WebConfiguration extends BaseConfig {

    /**
     * saas.log.enabled = true 并且 saas.log.type=DB时实例该类
     */
    @Bean
    @ConditionalOnExpression("${r'${'}saas.log.enabled:true${r'}'} && 'DB'.equals('${r'${'}saas.log.type:LOGGER${r'}'}')")
    public SysLogListener sysLogListener(LogApi logApi) {
        return new SysLogListener(logApi::save);
    }
}
