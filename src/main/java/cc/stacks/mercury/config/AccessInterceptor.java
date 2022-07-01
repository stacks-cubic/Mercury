package cc.stacks.mercury.config;

import cc.stacks.mercury.data.TokenData;
import cc.stacks.mercury.model.Token;
import cc.stacks.mercury.service.ProxyService;
import cc.stacks.mercury.util.TextUtil;
import com.alibaba.fastjson2.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 访问控制拦截器
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2022-01-17 19:02
 */
@Component
public class AccessInterceptor implements HandlerInterceptor {

    private final TokenData tokenData;
    private final ProxyService proxyService;
    private final Cache<String, Object> caffe;

    public AccessInterceptor(ProxyService proxyService, Cache<String, Object> caffe, TokenData tokenData) {
        this.proxyService = proxyService;
        this.caffe = caffe;
        this.tokenData = tokenData;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) return true;
        Method method = ((HandlerMethod) handler).getMethod();
        // 获取代理模式
        int proxyMode = SystemConfig.getInt("network:proxy:mode");
        // 代理指定来源
        if ("127.0.0.1:20200".equals(request.getHeader("Host"))) {
            if (proxyMode == 1) proxyService.direct("192.168.1.200", 9820, request, response);
            else if (proxyMode == 2 && !TextUtil.isNull(SystemConfig.get("network:zt:id")))
                proxyService.pierce("192.168.192.1", 9820, request, response);
            else return true;
            return false;
        } else {
            // 访问权限校验
            String checkAccessError = checkAccess(method.getAnnotation(Access.class), request);
            if (checkAccessError != null) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.print("{\"state\":false,\"message\":\"" + checkAccessError + "\",\"code\":" + 1000001 + ",\"time\":\""+System.currentTimeMillis()+"\"}");
                } catch (Exception ignored) {}
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) {
        // Controller 执行完毕后触发
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) {
        // DispatcherServlet 渲染视图之后触发(用不到)
    }

    // 访问权限校验
    private String checkAccess(Access access, HttpServletRequest request) {
        if (access != null) {
            // 从请求头获取认证信息
            String auth = getAuthorization(request);
            if (auth == null) return "拒绝访问,请先登录";
            String tag = "token:" + auth;
            // 获取令牌缓存
            caffe.getIfPresent(tag);
            Object cache = caffe.asMap().get(tag);
            // 解析令牌
            Token token = cache == null ? tokenData.getItem(auth) : JSON.parseObject(String.valueOf(cache), Token.class);
            // 校验令牌有效期
            String checkMsg = check(auth, token);
            if (checkMsg != null) return checkMsg;
            // 校验管理权限
            if (access.admin() && !token.getAdmin()) return "权限不足";
            // 将用户编号写入请求
            request.setAttribute("uid", token.getUid());
            // 将用户权限写入请求
            request.setAttribute("admin", token.getAdmin());
        }
        return null;
    }

    private String check(String auth, Token token) {
        if (token.getIssued() > System.currentTimeMillis()) return "访问令牌暂未生效";
        if (token.getExpire() < System.currentTimeMillis()) {
            // 移除失效令牌
            caffe.invalidate("token:" + auth);
            tokenData.delete(auth);
            return "访问令牌已过期";
        }
        return null;
    }

    private String getAuthorization(HttpServletRequest request) {
        try {
            String auth = request.getHeader("Authorization");
            if (TextUtil.isNull(auth)) auth = request.getHeader("authorization");
            if (TextUtil.isNull(auth)) return null;
            if (auth.contains("Bearer")) auth = auth.substring(7);
            if (auth.length() != 32) return null;
            return auth;
        } catch (Exception e) {
            return null;
        }
    }

}