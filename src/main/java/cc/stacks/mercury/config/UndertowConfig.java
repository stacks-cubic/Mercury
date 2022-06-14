package cc.stacks.mercury.config;

import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.DisallowedMethodsHandler;
import io.undertow.util.HttpString;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;

/**
 * Undertow 配置
 *
 * @author SkayZhang <skai-zhang@hotmail.com>
 * @date 2022-04-22 20:14
 */
@Configuration
public class UndertowConfig implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        Set<ErrorPage> errorPages = new HashSet<>();
        errorPages.add(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
        factory.setErrorPages(errorPages);

        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            WebSocketDeploymentInfo webSocketDeploymentInfo = new WebSocketDeploymentInfo();
            webSocketDeploymentInfo.setBuffers(new DefaultByteBufferPool(false, 1024));
            deploymentInfo.addServletContextAttribute("io.undertow.websockets.jsr.WebSocketDeploymentInfo", webSocketDeploymentInfo);
            deploymentInfo.addInitialHandlerChainWrapper(handler -> {
                HttpString[] disallowedHttpMethods = {HttpString.tryFromString("TRACE"),
                        HttpString.tryFromString("TRACK")};
                return new DisallowedMethodsHandler(handler, disallowedHttpMethods);
            });
        });
    }

}