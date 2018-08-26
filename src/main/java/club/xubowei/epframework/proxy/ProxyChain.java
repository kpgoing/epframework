package club.xubowei.epframework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author kpgoing
 * @date 2018/8/18.
 */
class ProxyChain {

    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method TargetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList;
    private int proxyIndex = 0;

    ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.TargetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    Class<?> getTargetClass() {
        return targetClass;
    }

    Method getTargetMethod() {
        return TargetMethod;
    }

    Object[] getMethodParams() {
        return methodParams;
    }

    Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }

        return methodResult;
    }
}
