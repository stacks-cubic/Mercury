package cc.stacks.mercury.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 访问控制拦截器
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2022-01-17 19:02
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Controller 执行之前触发
        if (!(handler instanceof HandlerMethod)) return true;
        Method method = ((HandlerMethod) handler).getMethod();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        // Controller 执行完毕后触发
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        // DispatcherServlet 渲染视图之后触发(用不到)
    }

}