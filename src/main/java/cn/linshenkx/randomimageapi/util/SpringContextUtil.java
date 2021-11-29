package cn.linshenkx.randomimageapi.util;

import com.google.common.collect.Lists;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        SpringContextUtil.applicationContext = applicationContext;
    }


    public static <T> List<T> getBeanList(Class<T> beanType) {
        Collection<T> tCollection = applicationContext.getBeansOfType(beanType).values();
        return Lists.newArrayList(tCollection);
    }

    public static <T, A extends Annotation> T getBeansWithAnnotaionValue(Class<T> beanType, Class<A> annotationClass, Object annotationValue) throws Exception {
        List<T> tCollection = getBeanList(beanType);
        for (T t : tCollection) {
            Class<?> targetClass = AopUtils.getTargetClass(t);
            A mode = targetClass.getAnnotation(annotationClass);
            if (null == mode) {
                throw new RuntimeException("not found beanType:" + beanType + ",annotationClass :" + annotationClass.getName());
            }
            Method method = annotationClass.getDeclaredMethod("value");

            if (annotationValue == method.invoke(mode)) {
                return t;
            }
        }
        throw new RuntimeException("not found beanType:" + beanType + ",annotationValue:" + annotationValue);
    }
}

