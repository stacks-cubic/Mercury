package cc.stacks.mercury.config;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}