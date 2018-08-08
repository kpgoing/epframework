package club.xubowei.epframework.helper;

import club.xubowei.epframework.annotation.Inject;
import club.xubowei.epframework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author zhisheng
 * @date 2018/8/6.
 */
public class IocHelper {

    static {

        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                Field[] beanClassDeclaredFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanClassDeclaredFields)) {
                    for (Field beanClassDeclaredField : beanClassDeclaredFields) {
                        if (beanClassDeclaredField.isAnnotationPresent(Inject.class)) {
                            Class<?> type = beanClassDeclaredField.getType();
                            Object beanFieldInstance = beanMap.get(type);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanClassDeclaredField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
