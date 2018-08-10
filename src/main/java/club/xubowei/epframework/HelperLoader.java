package club.xubowei.epframework;

import club.xubowei.epframework.helper.BeanHelper;
import club.xubowei.epframework.helper.ClassHelper;
import club.xubowei.epframework.helper.ControllerHelper;
import club.xubowei.epframework.helper.IocHelper;
import club.xubowei.epframework.util.ClassUtil;

/**
 * @author zhisheng
 * @date 2018/8/9.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> aClass : classes) {
            ClassUtil.loadClass(aClass.getName(), true);
        }
    }
}
