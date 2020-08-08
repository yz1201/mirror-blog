//package cn.dbdj1201.shiro.config;
//
//import cn.dbdj1201.shiro.component.JwtFilter;
//import cn.dbdj1201.shiro.component.UserRealm;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.crypto.hash.Sha256Hash;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.mgt.SessionsSecurityManager;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author yz1201
// * @date 2020-08-07 23:04
// **/
////@Configuration
//public class ShiroConfig {
//
//    @Autowired
//    JwtFilter jwtFilter;
//
//
//    // 配置自定义Realm
////    @Bean
////    public UserRealm userRealm() {
////        UserRealm userRealm = new UserRealm();
////        userRealm.setCredentialsMatcher(credentialsMatcher()); //配置使用哈希密码匹配
////        return userRealm;
////    }
//
//    // 配置url过滤器
////    @Bean
////    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
////        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
////
////        chainDefinition.addPathDefinition("/layuiadmin/**", "anon");
////        chainDefinition.addPathDefinition("/druid/**", "anon");
////        chainDefinition.addPathDefinition("/api/**", "anon");
////        chainDefinition.addPathDefinition("/user/**", "anon");
////        // all other paths require a logged in user
////        chainDefinition.addPathDefinition("/login", "anon");
////        return chainDefinition;
////    }
//
//    // 设置用于匹配密码的CredentialsMatcher
//    @Bean
//    public HashedCredentialsMatcher credentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);  // 散列算法，这里使用更安全的sha256算法
////        credentialsMatcher.setStoredCredentialsHexEncoded(false);  // 数据库存储的密码字段使用HEX还是BASE64方式加密
//        credentialsMatcher.setHashIterations(1024);  // 散列迭代次数
//        return credentialsMatcher;
//    }
//
//    // 配置security并设置userReaml，避免xxxx required a bean named 'authorizer' that could not be found.的报错
//    @Bean
//    public SessionsSecurityManager securityManager(UserRealm userRealm) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(userRealm);
//        securityManager.setCacheManager(cacheManager());
//        securityManager.setSessionManager(sessionManager());
//        return securityManager;
//    }
//
//
//    /**
//     * cacheManager 缓存 redis实现
//     * 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setExpire(1200);
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
//
//    /**
//     * 配置shiro redisManager
//     * 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setPassword("redis");
//        return redisManager;
//    }
//
//    /**
//     * Session Manager
//     * 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public DefaultWebSessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setSessionDAO(redisSessionDAO());
//        return sessionManager;
//    }
//
//    /**
//     * RedisSessionDAO shiro sessionDao层的实现 通过redis
//     * 使用的是shiro-redis开源插件
//     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }
//
//    @Bean
//    public ShiroFilterFactoryBean factory(SecurityManager securityManager) {
//        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//
//        // 添加自己的过滤器并且取名为jwt
//        Map<String, Filter> filterMap = new HashMap<>();
//        //设置我们自定义的JWT过滤器
//        filterMap.put("jwt", new JwtFilter());
//        factoryBean.setFilters(filterMap);
//        factoryBean.setSecurityManager(securityManager);
//        Map<String, String> filterRuleMap = new HashMap<>();
//        // 所有请求通过我们自己的JWT Filter
//        filterRuleMap.put("/**", "jwt");
//        // 访问 /unauthorized/** 不通过JWTFilter
//        filterRuleMap.put("/unauthorized/**", "anon");
//        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
//        return factoryBean;
//    }
//
//
//    /***
//     * 授权所用配置
//     *
//     * @return
//     */
//    @Bean
//    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
//        return defaultAdvisorAutoProxyCreator;
//    }
//
//    /***
//     * 使授权注解起作用不如不想配置可以在pom文件中加入
//     * <dependency>
//     *<groupId>org.springframework.boot</groupId>
//     *<artifactId>spring-boot-starter-aop</artifactId>
//     *</dependency>
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    /**
//     * Shiro生命周期处理器
//     */
//    @Bean
//    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }
//
//}
//
