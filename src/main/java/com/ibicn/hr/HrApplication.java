package com.ibicn.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//启用jpa 的审计功能,可以自动在字段上添加默认值
@EnableJpaAuditing
public class HrApplication extends SpringBootServletInitializer {

    //spring-boot提供的解决方案：生成tomcat服务器能管理的war包，而非内嵌的tomcat直接生成的jar包
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HrApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

}
