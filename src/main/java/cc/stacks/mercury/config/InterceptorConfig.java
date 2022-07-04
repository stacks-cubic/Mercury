package cc.stacks.mercury.config;

import cc.stacks.mercury.data.TokenData;
import cc.stacks.mercury.service.ProxyService;
import com.github.benmanes.caffeine.cache.Cache;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
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

    private final TokenData tokenData;
    private final UserAgentAnalyzer uaa;
    private final ProxyService proxyService;
    private final Cache<String, Object> caffe;

    public InterceptorConfig(ProxyService proxyService, UserAgentAnalyzer uaa, Cache<String, Object> caffe, TokenData tokenData) {
        this.proxyService = proxyService;
        this.uaa = uaa;
        this.caffe = caffe;
        this.tokenData = tokenData;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessInterceptor(proxyService,uaa,caffe,tokenData)).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}