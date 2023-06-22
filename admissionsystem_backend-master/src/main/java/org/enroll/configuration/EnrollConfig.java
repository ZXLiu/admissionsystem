package org.enroll.configuration;

import org.enroll.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration//声明当前是一个配置类，相当于是spring中的一个xml文件
public class EnrollConfig implements WebMvcConfigurer {//配置类(用于配置拦截器以及执行sql脚本)

    @Autowired
    LoginInterceptor interceptor;//拦截器

    @Value("classpath:sql/schema.sql")
    private Resource dataScript;//构建Resource对象


    @Override
    public void addInterceptors(InterceptorRegistry registry) {//拦截器配置
        InterceptorRegistration registration = registry.addInterceptor(interceptor);//添加拦截器
        registration.addPathPatterns("/**");//对所有请求都拦截
        registration.excludePathPatterns("/login/doLogin");//设置不需要拦截的过滤规则
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {//解决跨域问题
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Bean
    public HashMap<String, Object> globalStorage(){
        return new HashMap<>();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {//DataSourceInitializer类可以实现自动执行脚本的功能。通过自定义DataSourceInitializer Bean就可以实现按照业务要求执行特定的脚本。
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);//设置数据源
        initializer.setDatabasePopulator(databasePopulator());
        initializer.afterPropertiesSet();
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(dataScript);
        return populator;
    }
}
