package club.xubowei.epframework;

import club.xubowei.epframework.bean.Data;
import club.xubowei.epframework.bean.Handler;
import club.xubowei.epframework.bean.Param;
import club.xubowei.epframework.bean.View;
import club.xubowei.epframework.helper.BeanHelper;
import club.xubowei.epframework.helper.ConfigHelper;
import club.xubowei.epframework.helper.ControllerHelper;
import club.xubowei.epframework.util.JsonUtil;
import club.xubowei.epframework.util.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhisheng
 * @date 2018/8/12.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();

        ServletRegistration jspRegistration = servletContext.getServletRegistration("jsp");

        jspRegistration.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultRegistration = servletContext.getServletRegistration("default");

        defaultRegistration.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getMethod().toLowerCase();
        String requestPathInfo = request.getPathInfo();

        Handler handler = ControllerHelper.getHandler(requestMethod, requestPathInfo);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            Map<String, String[]> parameterMap = request.getParameterMap();
            Map<String, Object> params = new HashMap<>();
            parameterMap.forEach((key, value) -> params.put(key, value[0]));
            Param param = new Param(params);

            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(
                    controllerBean, actionMethod, param);
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        model.forEach(request::setAttribute);
                        request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }
    }
}
