package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Configuration
 *      标记为 一个配置类
 */
@Configuration
/**
 * @Description:
 * @Author: Guan FuQing
 * @Date: 2021/06/22 15:39
 * @Email: moumouguan@gmail.com
 */
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {

        // 初始化CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        // 允许的域,不要写 * (代表所有域名, 无法携带 cookie), 否则 cookie 就无法使用了
        config.addAllowedOrigin("http://manager.gmall.com");
        config.addAllowedOrigin("http://localhost:1000");
        config.addAllowedOrigin("http://127.0.0.1:1000");
        // 允许的头信息跨域, * 代表所有头信息
        config.addAllowedHeader("*");
        // 允许的请求方式跨域, * 代表所有方法
        config.addAllowedMethod("*");
        // 是否允许携带 Cookie 信息
        config.setAllowCredentials(true);

        // cors注册类: 添加映射路径, 拦截一切请求, 进行 cors 验证
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        /**
         * 注册 cors 配置
         *      path        路径
         *          /** 拦截所有路径
         *      config      配置对象, 通过配置对象允许那些域名来跨域
         */
        corsConfigurationSource.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(corsConfigurationSource);
    }
}