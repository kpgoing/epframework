package club.xubowei.epframework.helper;

import club.xubowei.epframework.annotation.Aspect;
import club.xubowei.epframework.annotation.Service;
import club.xubowei.epframework.proxy.AbstractAspectProxy;
import club.xubowei.epframework.proxy.Proxy;
import club.xubowei.epframework.proxy.ProxyManager;
import club.xubowei.epframework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kpgoing
 * @date 2018/8/25.
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {

            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            targetMap.forEach((key, value) -> {
                Object proxy = ProxyManager.createProxy(key, value);
                BeanHelper.setBean(key, proxy);
            });

        } catch (Exception e) {
            LOGGER.error("AOP util init failure", e);
        }
    }

    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {

        Class<? extends Annotation> annotationClass = aspect.value();
        Set<Class<?>> targetClassSet;

        if (!annotationClass.equals(Aspect.class)) {
            targetClassSet = ClassHelper.getClassSetByAnnotationPresent(annotationClass);
        } else {
            targetClassSet = new HashSet<>();
        }

        return targetClassSet;
    }


    /**
     * Generate a mapping of [proxy class: proxy class (target class)]
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();

        addAbstractAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);

        return proxyMap;
    }

    private static void addAbstractAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        proxyMap.putAll(
                ClassHelper.getClassSetBySuper(AbstractAspectProxy.class)
                        .stream()
                        .filter((cls) -> cls.isAnnotationPresent(Aspect.class))
                        .collect(Collectors.toMap(cls -> cls, cls -> createTargetClassSet(cls.getAnnotation(Aspect.class))))
        );
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        proxyMap.put(TransactionProxy.class, ClassHelper.getClassSetByAnnotationPresent(Service.class));
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {

        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();

        for (Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            for (Class<?> targetClass : entry.getValue()) {
                Proxy proxy = (Proxy) entry.getKey().newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    targetMap.put(targetClass, new ArrayList<>(Collections.singletonList(proxy)));
                }
            }
        }

        return targetMap;

    }

}
