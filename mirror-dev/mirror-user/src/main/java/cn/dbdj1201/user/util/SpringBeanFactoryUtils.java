package cn.dbdj1201.user.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: dbdj1201
 * @Date: 2020-08-06 19:22
 */

@Component

public class SpringBeanFactoryUtils implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public static Object getBean(Class type) {

        return context.getBean(type);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if (SpringBeanFactoryUtils.context == null) {

            SpringBeanFactoryUtils.context = applicationContext;

        }

    }

}