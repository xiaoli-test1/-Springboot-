package com.feit.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 登录页映射
        registry.addViewController("/loginview").setViewName("login");

        // 主页面映射（根路径）
        registry.addViewController("/").setViewName("main");

        // 其他路径（可选）
        registry.addViewController("/main").setViewName("main");

//        // 图书管理页面映射
//        registry.addViewController("/book/admin/manage").setViewName("book_manage");
//        registry.addViewController("/book/list").setViewName("book_list");
//        registry.addViewController("/book/admin/add").setViewName("book_form");
//        registry.addViewController("/book/read/{id}").setViewName("book_read");
//        registry.addViewController("/user/profile").setViewName("user_profile");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 添加自定义静态资源映射
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("classpath:/static/");
//    }
}