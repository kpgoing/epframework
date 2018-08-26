package club.xubowei.epframework.proxy;

/**
 * @author kpgoing
 * @date 2018/8/18.
 */
public interface Proxy {

    /**
     * Execute the facet logic and implement chained proxy by calling {@link ProxyChain#doProxyChain}.
     *
     * @param proxyChain Current agent chain.
     * @return result
     * @throws Throwable exception
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
