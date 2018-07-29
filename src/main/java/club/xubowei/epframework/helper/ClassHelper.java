package club.xubowei.epframework.helper;

import club.xubowei.epframework.annotation.Controller;
import club.xubowei.epframework.annotation.Service;
import club.xubowei.epframework.constant.ConfigConstant;
import club.xubowei.epframework.util.ClassUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zhisheng
 * @date 2018/7/29.
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getConfigByName(ConfigConstant.APP_BASE_PACKAGE);
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    private static Set<Class<?>> getClassSetByAnnotationPresent(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }

        return classSet;
    }

    public static Set<Class<?>> getServiceClassSet() {
        return getClassSetByAnnotationPresent(Service.class);
    }

    public static Set<Class<?>> getControllerClassSet() {
        return getClassSetByAnnotationPresent(Controller.class);
    }

    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }


}
