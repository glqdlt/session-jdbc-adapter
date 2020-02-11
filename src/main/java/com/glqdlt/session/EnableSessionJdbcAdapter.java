package com.glqdlt.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Jhun
 * 2020-02-11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(JdbcSessionAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
public @interface EnableSessionJdbcAdapter {
}
