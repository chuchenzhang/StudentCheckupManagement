package com.tphy.tsykxstj.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * springboot 拦截器 自定义静态资源路径
 *
 * @author chuchen
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域配置类
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        //设置允许跨域的路径
        registry.addMapping("/**")
                //是否发送Cookie
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //放行哪些请求方式
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //.allowedMethods("*") //或者放行全部
                //放行哪些原始请求头部信息
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600)
        ;
    }
}


