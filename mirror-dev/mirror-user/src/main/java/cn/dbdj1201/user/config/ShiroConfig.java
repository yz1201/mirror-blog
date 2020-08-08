//package cn.dbdj1201.user.config;
//
//import cn.dbdj1201.user.shiro.AccountRealm;
//import cn.dbdj1201.user.shiro.JwtFilter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.util.ThreadContext;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
//
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//import java.util.*;
//
///**
// * @Author: dbdj1201
// * @Date: 2020-08-04 9:16
// * Shiro启用注解拦截控制器
// */
//@Configuration
//@Slf4j
//public class ShiroConfig {
//    /*
//        引入RedisSessionDAO和RedisCacheManager，为了解决shiro的权限数据和会话信息能保存到redis中，实现会话共享。
//
//        重写了SessionManager和DefaultWebSecurityManager，同时在DefaultWebSecurityManager中为了关闭shiro自带的session方式，
//    我们需要设置为false，这样用户就不再能通过session方式登录shiro。后面将采用jwt凭证登录。
//
//        在ShiroFilterChainDefinition中，我们不再通过编码形式拦截Controller访问路径，而是所有的路由都需要经过JwtFilter这个过滤器，
//    然后判断请求头中是否含有jwt的信息，有就登录，没有就跳过。跳过之后，有Controller中的shiro注解进行再次拦截，比如@RequiresAuthentication，这样控制权限访问。
//    */
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
////    @Bean
////    public RedisCacheManager cacheManager() {
////        RedisCacheManager redisCacheManager = new RedisCacheManager();
////        redisCacheManager.setRedisManager(redisManager());
////        //redis中针对不同用户缓存
////        redisCacheManager.setPrincipalIdFieldName("username");
////        //用户权限信息缓存时间
////        redisCacheManager.setExpire(200000);
////        log.info("redis config info-{} {} {}", redisCacheManager.getExpire(),
////                redisCacheManager.getPrincipalIdFieldName(),
////                redisCacheManager.getRedisManager());
////        return redisCacheManager;
////    }
////
////
////    @Bean
////    public RedisManager redisManager() {
////        //        redisManager.setHost("127.0.0.1:6379");
////        return new RedisManager();
////    }
////
////
////    @Bean
////    public SessionManager sessionManager() {
////        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
////        // inject redisSessionDAO
////        sessionManager.setSessionDAO(redisSessionDAO());
////        return sessionManager;
////
////        /*
////         DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
////        Collection<SessionListener> listeners = new ArrayList<>();
////        listeners.add(new ShiroSessionListener());
////        // 设置 session超时时间
////        sessionManager.setGlobalSessionTimeout(febsProperties.getShiro().getSessionTimeout() * 1000L);
////        sessionManager.setSessionListeners(listeners);
////        sessionManager.setSessionDAO(redisSessionDAO());
////        sessionManager.setSessionIdUrlRewritingEnabled(false);
////        */
////    }
////
////    @Bean
////    public RedisSessionDAO redisSessionDAO() {
////        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
////        redisSessionDAO.setRedisManager(redisManager());
////        return redisSessionDAO;
////    }
//
//
////    @Bean
////    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm) {
////        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);
////        securityManager.setSessionManager(sessionManager());
////        securityManager.setCacheManager(cacheManager());
////        //关闭shiro自带的session
//////        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//////        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//////        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//////        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//////        securityManager.setSubjectDAO(subjectDAO);
////        ThreadContext.bind(securityManager);
////        return securityManager;
////    }
//
//
////    @Bean
////    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
////        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
////        Map<String, String> filterMap = new LinkedHashMap<>();
////        filterMap.put("/**", "jwt");
////        chainDefinition.addPathDefinitions(filterMap);
////        return chainDefinition;
////    }
////
////    @Bean("shiroFilterFactoryBean")
////    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
////                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
////        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
////        shiroFilter.setSecurityManager(securityManager);
////        Map<String, Filter> filters = new HashMap<>();
////        filters.put("jwt", jwtFilter);
////        shiroFilter.setFilters(filters);
////        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();
////        shiroFilter.setFilterChainDefinitionMap(filterMap);
////        return shiroFilter;
////    }
//
//
////    // 开启注解代理（默认好像已经开启，可以不要）
////    @Bean
////    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
////        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
////        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
////        return authorizationAttributeSourceAdvisor;
////    }
////
////    @Bean
////    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
////        return new DefaultAdvisorAutoProxyCreator();
////    }
//
//
//}
