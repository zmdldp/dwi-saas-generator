package com.dwi.saas.generator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SuperClass {

    SUPER_CLASS("com.dwi.basic.base.controller.SuperController", "com.dwi.basic.base.service.SuperService",
            "com.dwi.basic.base.service.SuperServiceImpl", "com.dwi.basic.base.mapper.SuperMapper"),
    SUPER_CACHE_CLASS("com.dwi.basic.base.controller.SuperCacheController", "com.dwi.basic.base.service.SuperCacheService",
            "com.dwi.basic.base.service.SuperCacheServiceImpl", "com.dwi.basic.base.mapper.SuperMapper"),
    NONE("", "", "", "");

    private String controller;
    private String service;
    private String serviceImpl;
    private String mapper;

    public SuperClass setController(String controller) {
        this.controller = controller;
        return this;
    }

    public SuperClass setService(String service) {
        this.service = service;
        return this;
    }

    public SuperClass setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    public SuperClass setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }
}
