package com.healthapp.notificationservice.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    // Method is called by Spring to set the application context
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    // Allows other parts of the code to retrieve Spring beans by name
    public static Object getBean(String beanName) {
        return CONTEXT.getBean(beanName);
    }
}