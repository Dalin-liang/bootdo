package com.bootdo.system.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.bootdo.common.config.Constant;
import com.bootdo.common.redis.shiro.RedisCacheManager;
import com.bootdo.common.redis.shiro.RedisManager;
import com.bootdo.common.redis.shiro.RedisSessionDAO;
import com.bootdo.system.shiro.UserRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

//import org.apache.shiro.cache.CacheManager;

/**
 * @author bootdo 1992lcg@163.com
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.cache.type}")
    private String cacheType ;

    @Value("${server.session-timeout}")
    private int tomcatTimeout;

    /*@Value("${cas.server-url}")
    private String casServerUrlPrefix;
    @Value("${cas.service}")
    private String shiroServerUrlPrefix;*/

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 添加casFilter到shiroFilter中
        /*Map<String, Filter> filters = new HashMap<>();
        filters.put("casFilter", casFilter());
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);*/

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setLoginUrl(casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + "/cas");//cas统一登录

        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");


        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //filterChainDefinitionMap.put("/app/Emergency.apk","anon");
        filterChainDefinitionMap.put("/app/**","anon");
        /*filterChainDefinitionMap.put("/app/show**","anon");
        filterChainDefinitionMap.put("/app/personTrajectory","anon");
        filterChainDefinitionMap.put("/app/dowloadapk","anon");*/

        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/loginValidateCode", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/files/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/blog", "anon");
        filterChainDefinitionMap.put("/dispatch/**", "anon");
        filterChainDefinitionMap.put("/blog/open/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/receiveInfo/**", "anon");
        filterChainDefinitionMap.put("/deptPerson/getAll2", "anon");
        filterChainDefinitionMap.put("/planManage/planMain/getSelectData", "anon");
        filterChainDefinitionMap.put("/baiyi/**", "anon");

        /*filterChainDefinitionMap.put("/cas", "casFilter");
        filterChainDefinitionMap.put("/logout", "logout");*/
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(userRealm());
//        securityManager.setRealms(Lists.newArrayList(userRealm(),shiroCasRealm()));
        // 自定义缓存实现 使用redis
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            securityManager.setCacheManager(rediscacheManager());
        } else {
            securityManager.setCacheManager(ehCacheManager());
        }
        securityManager.setSessionManager(sessionManager());
//        securityManager.setSubjectFactory(new CasSubjectFactory());
        return securityManager;
    }

    @Bean
    UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /*@Bean(name = "shiroCasRealm")
    public ShiroCasRealm shiroCasRealm() {
        ShiroCasRealm realm = new ShiroCasRealm();
        realm.setCasServerUrlPrefix(casServerUrlPrefix);
        realm.setCasService(shiroServerUrlPrefix + "/cas");
        return realm;
    }*/

    ///**
    // * 注册单点登出listener
    // *
    // * @return
    // */
    //@Bean
    //public ServletListenerRegistrationBean singleSignOutHttpSessionListener() {
    //    ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
    //    bean.setListener(new SingleSignOutHttpSessionListener());
    //    bean.setEnabled(true);
    //    return bean;
    //}

    /**
     * 注册单点登出filter
     *
     * @return
     */
    /*@Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        return bean;
    }*/



    /**
     * CAS过滤器
     * @return
     */
    /*public CasFilter casFilter() {
        String loginUrl = casServerUrlPrefix + "/login?service=" + shiroServerUrlPrefix + "/cas";
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面
        return casFilter;
    }*/

    /**
     * 登出过滤器
     * @return
     */
    /*public LogoutFilter logoutFilter() {
        String logoutUrl = casServerUrlPrefix + "/logout?service=" + shiroServerUrlPrefix + "/cas";
        LogoutFilter logout = new LogoutFilter();
        logout.setRedirectUrl(logoutUrl);
        return logout;
    }*/



    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        //redisManager.setTimeout(1800);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager rediscacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionDAO sessionDAO() {
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            return redisSessionDAO();
        } else {
            return new MemorySessionDAO();
        }
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
        sessionManager.setSessionDAO(sessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new BDSessionListener());
//        listeners.add(new ShiroSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(cacheManager());
        return em;
    }

    @Bean("cacheManager2")
    CacheManager cacheManager(){
        return CacheManager.create();
    }


}
