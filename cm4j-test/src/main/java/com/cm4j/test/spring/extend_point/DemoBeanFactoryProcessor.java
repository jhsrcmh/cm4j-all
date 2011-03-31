package com.cm4j.test.spring.extend_point;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class DemoBeanFactoryProcessor implements BeanFactoryPostProcessor {

    /**
     * 本类继承ApplicationContextAware也可实现同样功能，因为context也可获得beanFactory
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 创建默认的资源加载器
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();

        // 由 资源加载器 获取资源
        Resource resource = defaultResourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX
                + "beanfactory-extend.xml");

        // 创建xml文件的Bean解析器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
                (BeanDefinitionRegistry) beanFactory);

        // 解析xml并注册信息到 BeanDefinition
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
    }

}
