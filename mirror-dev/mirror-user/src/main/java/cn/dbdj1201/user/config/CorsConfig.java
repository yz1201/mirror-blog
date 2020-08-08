package cn.dbdj1201.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-04 11:26
 * 解决跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "HEAD", "DELETE", "OPTION")
                .allowCredentials(true)
                .maxAge(3600)
//                .allowedHeaders("Access-Control-Allow-Credentials: true")
                .allowedHeaders("*");
    }
}
