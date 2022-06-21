package cc.stacks.mercury.config;

import cc.stacks.mercury.service.zerotier.ZtCoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartConfig implements CommandLineRunner {

    @Value("${mercury.init}")
    private Boolean initState;

    private final ZtCoreService ztCoreService;

    public StartConfig(ZtCoreService ztCoreService) {
        this.ztCoreService = ztCoreService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (initState) ztCoreService.init();
    }

}
