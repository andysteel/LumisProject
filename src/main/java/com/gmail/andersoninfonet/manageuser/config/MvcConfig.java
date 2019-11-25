package com.gmail.andersoninfonet.manageuser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	   @Override
	    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		   	registry.addResourceHandler("swagger-ui.html")
		   		.addResourceLocations("classpath:/META-INF/resources/")
		   		.resourceChain(true)
		   		.addResolver(new PathResourceResolver());
	        registry.addResourceHandler("/webjars/**")
	        		.addResourceLocations("classpath:/META-INF/resources/webjars/")
	        		.resourceChain(true)
	        		.addResolver(new PathResourceResolver());
	        registry.addResourceHandler("/swagger-resources/**")
    				.addResourceLocations("classpath:/META-INF/swagger-resources/")
    				.resourceChain(true)
    				.addResolver(new PathResourceResolver());
	    }
}
