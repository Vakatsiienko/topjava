package ru.javawebinar.topjava.core;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Iaroslav on 2/22/2017.
 */
public class ApplicationContextHolder {
    public static ConfigurableApplicationContext applicationContext;

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        ApplicationContextHolder.applicationContext = applicationContext;
    }
}
