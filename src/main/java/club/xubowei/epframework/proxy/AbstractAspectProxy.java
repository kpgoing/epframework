package club.xubowei.epframework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author kpgoing
 * @date 2018/8/24.
 */
public abstract class AbstractAspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
        } finally {
            end();
        }

        return result;

    }

    private void begin() {
    }

    private boolean intercept(Class<?> targetClass, Method targetMethod, Object[] methodParams) {
        return true;
    }

    private void before(Class<?> targetClass, Method targetMethod, Object[] methodParams) {

    }

    private void after(Class<?> targetClass, Method targetMethod, Object[] methodParams) {

    }

    private void error(Class<?> targetClass, Method targetMethod, Object[] methodParams, Throwable error) {

    }

    private void end() {

    }
}
