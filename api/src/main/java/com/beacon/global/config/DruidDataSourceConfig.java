package com.beacon.global.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Druid的DataResource配置类
 *
 * @author luckyhua
 * @version 1.0
 * @since 2017/9/19
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(DruidDataSourceConfig.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment env) {
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }

    @Bean
    public DataSource dataSource() {
        log.info("ZGH10000: inject druid database connection pool into spring boot container .");
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(propertyResolver.getProperty("url"));
        datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        datasource.setUsername(propertyResolver.getProperty("username"));
        datasource.setPassword(propertyResolver.getProperty("password"));
        datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initial-size")));
        datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("min-idle")));
        datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("max-wait")));
        datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("max-active")));
        datasource.setMinEvictableIdleTimeMillis(Long.valueOf(propertyResolver.getProperty("min-evictable-idle-time-millis")));
        datasource.setTimeBetweenEvictionRunsMillis(Long.valueOf(propertyResolver.getProperty("time-between-eviction-runs-millis")));
        datasource.setPoolPreparedStatements(Boolean.parseBoolean(propertyResolver.getProperty("poolPreparedStatements")));
        datasource.setTestOnBorrow(Boolean.parseBoolean(propertyResolver.getProperty("test-on-borrow")));
        datasource.setTestOnReturn(Boolean.parseBoolean(propertyResolver.getProperty("test-on-return")));
        datasource.setTestWhileIdle(Boolean.parseBoolean(propertyResolver.getProperty("test-while-idle")));

        List<Filter> filters = new ArrayList<>();
//        filters.add(statFilter());
//        filters.add(wallFilter());
//        filters.add(logFilter());
        datasource.setProxyFilters(filters);
        return datasource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        StatViewServlet statViewServlet = new StatViewServlet();
        registrationBean.setServlet(statViewServlet);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/druid/*");
        registrationBean.setUrlMappings(urlPatterns);

        Map<String, String> initParams = new HashMap<>();
//        initParams.put("allow", "127.0.0.1,192.168.1.146"); // IP白名单 (没有配置或者为空，则允许所有访问)
//        initParams.put("deny", "192.168.1.111"); // IP黑名单 (存在共同时，deny优先于allow)
        initParams.put("loginUsername", "root");// 用户名
        initParams.put("loginPassword", "admin");// 密码
        initParams.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
        registrationBean.setInitParameters(initParams);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean druidFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        WebStatFilter webStatFilter = new WebStatFilter();
        registrationBean.setFilter(webStatFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"); //忽略资源
        registrationBean.setInitParameters(initParams);
        return registrationBean;
    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);

        return statFilter;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();

        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }

    @Bean
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter filter = new Slf4jLogFilter();
        filter.setResultSetLogEnabled(false);
        filter.setConnectionLogEnabled(false);
        filter.setConnectionConnectBeforeLogEnabled(false);
        filter.setConnectionConnectAfterLogEnabled(false);
        filter.setConnectionCloseAfterLogEnabled(false);
        filter.setConnectionCommitAfterLogEnabled(false);
        filter.setConnectionRollbackAfterLogEnabled(false);

        filter.setStatementParameterClearLogEnable(false);
        filter.setStatementCreateAfterLogEnabled(false);
        filter.setStatementCloseAfterLogEnabled(false);
        filter.setStatementParameterSetLogEnabled(false);
        filter.setStatementPrepareAfterLogEnabled(false);
        return filter;
    }
}
