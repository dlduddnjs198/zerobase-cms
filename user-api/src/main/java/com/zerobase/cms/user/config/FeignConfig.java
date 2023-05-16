package com.zerobase.cms.user.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor("api", "b868ac54537ad3d1679851258bdd48b4-db4df449-4a956474");
    }
}
