package ${packageBase}.config.mq;

import com.alibaba.fastjson.JSONObject;
import com.dwi.saas.common.constant.BizMqQueue;
import com.dwi.basic.database.properties.DatabaseProperties;
import com.dwi.basic.mq.properties.MqProperties;
import com.dwi.saas.tenant.dto.DataSourcePropertyDTO;
import com.dwi.saas.tenant.service.DataSourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;

import static com.dwi.saas.common.constant.BizConstant.INIT_DS_PARAM_METHOD;
import static com.dwi.saas.common.constant.BizConstant.INIT_DS_PARAM_METHOD_INIT;
import static com.dwi.saas.common.constant.BizConstant.INIT_DS_PARAM_TENANT;

/**
 * 消息队列开启时，启用
 *
 * @author ${author}
 * @date ${date}
 */
@Slf4j
@AllArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
public class ${service}TenantDatasourceConfiguration {
    /** 建议将该变量手动移动到： BizMqQueue 类 */
    private final static String TENANT_DS_QUEUE_BY_${service?upper_case} = "tenant_ds_${service?lower_case}";
    private final static String TENANT_DS_FANOUT_EXCHANGE_${service?upper_case} = "tenant_ds_fe_${service?lower_case}";
    private final DataSourceService dataSourceService;

    @Bean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public Queue dsQueue() {
        return new Queue(TENANT_DS_QUEUE_BY_${service?upper_case});
    }

    @Bean
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public Binding dsQueueBinding() {
        return new Binding(TENANT_DS_QUEUE_BY_${service?upper_case}, Binding.DestinationType.QUEUE, TENANT_DS_FANOUT_EXCHANGE_${service?upper_case}, "", new HashMap(1));
    }

    @RabbitListener(queues = TENANT_DS_QUEUE_BY_${service?upper_case})
    @ConditionalOnProperty(prefix = DatabaseProperties.PREFIX, name = "multiTenantType", havingValue = "DATASOURCE")
    public void dsRabbitListener(@Payload String param) {
        log.debug("异步初始化数据源=={}", param);
        JSONObject map = JSONObject.parseObject(param);
        if (INIT_DS_PARAM_METHOD_INIT.equals(map.getString(INIT_DS_PARAM_METHOD))) {
            dataSourceService.initConnect(map.getObject(INIT_DS_PARAM_TENANT, DataSourcePropertyDTO.class));
        } else {
            dataSourceService.remove(map.getString(INIT_DS_PARAM_TENANT));
        }
    }
}
