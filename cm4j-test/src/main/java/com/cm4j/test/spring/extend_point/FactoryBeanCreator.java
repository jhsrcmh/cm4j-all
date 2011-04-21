package com.cm4j.test.spring.extend_point;

import org.springframework.beans.factory.FactoryBean;

/**
 * 实现了FactoryBean的对象在getBean()的时候是返回具体的实例而不是FactoryBeanCreator对象实例<br />
 * 如果要获取 FactoryBeanCreator对象实例 ， 则要调用 getBean(BeanFactory.FACTORY_BEAN_PREFIX +
 * beanName)
 * 
 * 
 * @author yanghao
 * 
 */
public class FactoryBeanCreator implements FactoryBean<String> {

	@Override
	public String getObject() throws Exception {
		return "ABC";
	}

	@Override
	public Class<?> getObjectType() {
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
