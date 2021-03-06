saas:
  nacos:
    ip: ${r"${"}NACOS_IP:@nacos.ip@${r"}"}
    port: ${r"${"}NACOS_PORT:@nacos.port@${r"}"}
    namespace: ${r"${"}NACOS_ID:@nacos.namespace@${r"}"}
    username: ${r"${"}NACOS_ID:@nacos.username@${r"}"}
    password: ${r"${"}NACOS_ID:@nacos.password@${r"}"}

spring:
  main:
    allow-bean-definition-overriding: true
  application:
    name: @project.artifactId@
  profiles:
    active: @profile.active@
  cloud:
    nacos:
      config:
        server-addr: ${r"${"}saas.nacos.ip${r"}"}:${r"${"}saas.nacos.port${r"}"}
        file-extension: yml
        namespace: ${r"${"}saas.nacos.namespace${r"}"}
        shared-configs:
          - dataId: common.yml
            refresh: true
          - dataId: redis.yml
            refresh: false
          - dataId: mysql.yml
            refresh: true
          - dataId: rabbitmq.yml
            refresh: false
        enabled: true
        username: ${r"${"}saas.nacos.username${r"}"}
        password: ${r"${"}saas.nacos.password${r"}"}
      discovery:
        username: ${r"${"}saas.nacos.username${r"}"}
        password: ${r"${"}saas.nacos.password${r"}"}
        server-addr: ${r"${"}saas.nacos.ip}:${r"${"}saas.nacos.port${r"}"}
        namespace: ${r"${"}saas.nacos.namespace${r"}"}
        metadata: # 元数据，用于权限服务实时获取各个服务的所有接口
          management.context-path: ${r"${"}server.servlet.context-path:${r"}"}${r"${"}spring.mvc.servlet.path:${r"}"}${r"${"}management.endpoints.web.base-path:${r"}"}

logging:
  file:
    path: @logging.file.path@
    name: ${r"${"}logging.file.path${r"}"}/${r"${"}spring.application.name}/root.log

# 用于/actuator/info
info:
  name: '@project.name@'
  description: '@project.description@'
  version: '@project.version@'
