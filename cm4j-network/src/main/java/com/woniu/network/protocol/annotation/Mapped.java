package com.woniu.network.protocol.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表明该字段与xml配置中的字段一致，且字段值将在组装IProtocol时被赋值
 * 
 * @author yang.hao
 * @since 2011-10-31 下午1:51:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Mapped{
	
}
