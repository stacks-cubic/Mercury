package cc.stacks.mercury.config;

import cc.stacks.mercury.service.ProxyService;
import cc.stacks.mercury.service.zerotier.ZtSocketService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2022-01-17 19:02
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final ZtSocketService ztSocketService;
    private final ProxyService proxyService;

    public InterceptorConfig(ZtSocketService ztSocketService,ProxyService proxyService) {
        this.ztSocketService = ztSocketService;
        this.proxyService = proxyService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor(ztSocketService,proxyService)).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}