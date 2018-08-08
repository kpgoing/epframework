package club.xubowei.epframework.helper;

import club.xubowei.epframework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhisheng
 * @date 2018/8/6.
 */
public class BeanHelper {

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> aClass : beanClassSet) {
            Object object = ReflectionUtil.newInstance(aClass);
            BEAN_MAP.put(aClass, object);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class: " + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }



}
