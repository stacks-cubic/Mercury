package cc.stacks.mercury.config;

import cc.stacks.mercury.service.ProxyService;
import cc.stacks.mercury.service.zerotier.ZtSocketService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问控制拦截器
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2022-01-17 19:02
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private final ZtSocketService ztSocketService;
    private final ProxyService proxyService;

    public AccessInterceptor(ZtSocketService ztSocketService, ProxyService proxyService) {
        this.ztSocketService = ztSocketService;
        this.proxyService = proxyService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String host = request.getHeader("Host");
        if ("192.168.1.160:20200".equals(host)) {
            // 穿透
            proxyService.pierce("192.168.192.1", 9820, request, response);
            // 直连
//            proxyService.direct("192.168.1.200", 9820, request, response);
            return false;
        } else if (!(handler instanceof HandlerMethod)) return true;
        else return true;
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