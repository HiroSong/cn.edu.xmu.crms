package cn.edu.xmu.crms.util.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SongLingbing
 * @date 2018/12/25 18:01
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //配置用户名
        reg.addInitParameter("loginUsername", "root");
        //配置密码
        reg.addInitParameter("loginPassword", "Jingli+22");
        //在日志中打印执行慢的sql语句
        reg.addInitParameter("logSlowSql", "true");
        //另外还可配置黑白名单等信息，可参考druid官网介绍
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        //过滤文件类型
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        //监控单个url调用的sql列表
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

}
