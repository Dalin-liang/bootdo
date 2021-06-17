package com.bootdo.common.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MappingConfig extends WebMvcConfigurerAdapter {
	
	 @Value("${bootdo.mappingPath}")
	 private String mappingUrl;
	 @Value("${bootdo.uploadPath}")
	 private String uploadUrl;
	
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {        

		registry.addResourceHandler(mappingUrl+"/**").addResourceLocations("file:"+ uploadUrl + File.separator);         
		super.addResourceHandlers(registry);   
		}
	

}
