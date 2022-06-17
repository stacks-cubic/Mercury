package cc.stacks.mercury.config;

import cc.stacks.mercury.service.ZtSocketService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public InterceptorConfig(ZtSocketService ztSocketService) {
        this.ztSocketService = ztSocketService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor(ztSocketService)).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}