package com.bootdo.system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * app上传图片路径
     */
    @Value("${bootdo.uploadPath}")
    private String upLoadFile;

    public void  addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("appFile/**").addResourceLocations("file:" + upLoadFile);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
