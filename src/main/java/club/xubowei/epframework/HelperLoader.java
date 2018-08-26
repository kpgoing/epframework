package club.xubowei.epframework;

import club.xubowei.epframework.helper.*;
import club.xubowei.epframework.util.ClassUtil;

/**
 * @author zhisheng
 * @date 2018/8/9.
 */
final class HelperLoader {
    static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classes) {
            ClassUtil.loadClass(aClass.getName(), true);
        }
    }
}
