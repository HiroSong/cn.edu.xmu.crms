//package cn.edu.xmu.crms.config;
//
//import org.apache.shiro.authc.credential.CredentialsMatcher;
//import org.apache.shiro.spring.LifecycleBeanPostProcessor;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//
//import java.util.LinkedHashMap;
//
///**
// * @author SongLingbing
// * @date 2018/11/29 22:21
// */
//public class ShiroConfig {
//    @Bean
//    public DefaultWebSecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
//        System.err.println("--------------shiro已经加载----------------");
//        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
//        manager.setRealm(authRealm);
//        return manager;
//    }
//    //过滤器
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
//        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
//        bean.setSecurityManager(manager);
//        //配置登录的url和登录成功的url
//        bean.setLoginUrl("/index.jsp");
//        bean.setSuccessUrl("/home");
//        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/api/login", "anon");
//        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return bean;
//    }
//    @Bean
//    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
//        AuthRealm authRealm=new AuthRealm();
//        authRealm.setCredentialsMatcher(matcher);
//        return authRealm;
//    }
//    @Bean
//    public CredentialsMatcher credentialsMatcher() {
//        return new AuthCredential();
//    }
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }
//    @Bean
//    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
//        creator.setProxyTargetClass(true);
//        return creator;
//    }
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager manager) {
//        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(manager);
//        return advisor;
//    }
//}
