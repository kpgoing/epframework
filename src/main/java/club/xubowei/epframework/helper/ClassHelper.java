package club.xubowei.epframework.helper;

import club.xubowei.epframework.annotation.Controller;
import club.xubowei.epframework.annotation.Service;
import club.xubowei.epframework.constant.ConfigConstant;
import club.xubowei.epframework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    static Set<Class<?>> getClassSetByAnnotationPresent(Class<? extends Annotation> annotationClass) {

        return CLASS_SET.stream()
                .filter((cls) -> cls.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());

    }

    private static Set<Class<?>> getServiceClassSet() {
        return getClassSetByAnnotationPresent(Service.class);
    }

    static Set<Class<?>> getControllerClassSet() {
        return getClassSetByAnnotationPresent(Controller.class);
    }

    static Set<Class<?>> getBeanClassSet() {

        Set<Class<?>> beanClassSet = new HashSet<>();

        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());

        return beanClassSet;

    }

    static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {

        return CLASS_SET.stream()
                .filter(((Predicate<Class<?>>) superClass::isAssignableFrom).
                        and(((Predicate<Class<?>>) superClass::equals)
                                .negate()))
                .collect(Collectors.toSet());

    }






}
