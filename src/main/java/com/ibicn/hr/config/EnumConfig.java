package com.ibicn.hr.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class EnumConfig extends FastJsonConfig {

    @Bean
    public SerializeConfig FastJsonConfig() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //获取指定包名下所有的有JsonFormat注解的类，因为这样可以兼容jackson的enum转换
        //然后在springmvc转json时，就会把标记有JsonFormat注解的枚举中的get方法转为json格式，当然也可以自定义一个注解
        Set<Class<?>> classes = ClassUtil.getClasses("com.ibicn.ENUM", true, JsonFormat.class);
        SerializeConfig serializeConfig = getSerializeConfig();
        for (Class<?> clazz : classes) {
            Class<? extends Enum<?>> eclazz = (Class<? extends Enum<?>>) clazz;
            serializeConfig.configEnumAsJavaBean(eclazz);
        }
        return serializeConfig;
    }
}
