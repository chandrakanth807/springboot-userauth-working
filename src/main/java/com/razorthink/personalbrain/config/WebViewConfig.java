package com.razorthink.personalbrain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebViewConfig extends WebMvcConfigurerAdapter {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex()
    {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void addViewControllers( ViewControllerRegistry registry )
            {
                registry.addRedirectViewController("/rest", "/api/rest");
                registry.addViewController("/").setViewName("forward:/pages/index.html");
                registry.addViewController("/error").setViewName("forward:/pages/error/500.html");

                registry.addRedirectViewController("/docs/images", "/images");
                registry.addRedirectViewController("/docs/v2/api-docs", "/v2/api-docs");
                registry.addRedirectViewController("/docs/configuration/ui", "/configuration/ui");
                registry.addRedirectViewController("/docs/configuration/security", "/configuration/security");
                registry.addRedirectViewController("/docs/swagger-resources", "/swagger-resources");
                registry.addRedirectViewController("/docs", "/docs/swagger-ui.html");
                registry.addRedirectViewController("/docs/", "/docs/swagger-ui.html");
            }
        };
    }

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry )
    {
        registry.addResourceHandler("/docs/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer )
    {
        configurer.enable();
    }

    // @Bean
    // public ViewResolver getViewResolver()
    // {
    // InternalResourceViewResolver resolver = new
    // InternalResourceViewResolver();
    // resolver.setPrefix("/");
    // resolver.setSuffix(".html");
    // return resolver;
    // }

}