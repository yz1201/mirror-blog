package cn.dbdj1201.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-03 19:24
 */
@Configuration
@MapperScan("cn.dbdj1201.user.mapper")
@EnableTransactionManagement
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
