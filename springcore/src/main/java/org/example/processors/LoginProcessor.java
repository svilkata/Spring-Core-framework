package org.example.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class LoginProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = Logger.getLogger(LoginProcessor.class.getName());

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("Before bean %s is initialized ...".formatted(beanName));
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        LOGGER.info("After bean %s is initialized ...".formatted(beanName));
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
