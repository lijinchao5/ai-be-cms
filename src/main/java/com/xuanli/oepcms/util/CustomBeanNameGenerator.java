package com.xuanli.oepcms.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		// TODO Auto-generated method stub
//		System.out.println(definition.getBeanClassName());
		return definition.getBeanClassName();
	}
	
}
