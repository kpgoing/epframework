package club.xubowei.epframework.helper;

import club.xubowei.epframework.annotation.Action;
import club.xubowei.epframework.bean.Handler;
import club.xubowei.epframework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhisheng
 * @date 2018/8/7.
 */
public class ControllerHelper {

    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();

        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] controllerClassMethods = controllerClass.getMethods();
                if (ArrayUtils.isNotEmpty(controllerClassMethods)) {
                    for (Method controllerClassMethod : controllerClassMethods) {
                        if (controllerClassMethod.isAnnotationPresent(Action.class)) {
                            String path = controllerClassMethod.getAnnotation(Action.class).value();
                            Request request = new Request(controllerClassMethod.getName(), path);
                            Handler handler = new Handler(controllerClass, controllerClassMethod);
                            ACTION_MAP.put(request, handler);
                        }
                    }
                }
            }
        }
    }
}
