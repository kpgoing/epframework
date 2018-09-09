package club.xubowei.epframework.proxy;

import club.xubowei.epframework.annotation.Transaction;
import club.xubowei.epframework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author kpgoing
 * @date 2018/9/9.
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();

        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);

            try {
                DatabaseHelper.beginTransaction();
                LOGGER.debug("begin transaction");

                result = proxyChain.doProxyChain();

                DatabaseHelper.commitTransaction();
                LOGGER.debug("commit transaction");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                LOGGER.debug("rollback transaction");

                throw e;
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }

}
