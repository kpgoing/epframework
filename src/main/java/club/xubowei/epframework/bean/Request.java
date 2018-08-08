package club.xubowei.epframework.bean;

import java.util.Objects;

/**
 * @author zhisheng
 * @date 2018/8/7.
 */
public class Request {

    private String requestMethod;

    private String requestPath;

    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Request request = (Request) object;
        return Objects.equals(requestMethod, request.requestMethod) &&
                Objects.equals(requestPath, request.requestPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(requestMethod, requestPath);
    }
}
