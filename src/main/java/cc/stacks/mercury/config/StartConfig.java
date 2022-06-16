package cc.stacks.mercury.config;

import cc.stacks.mercury.service.ZtCoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartConfig implements CommandLineRunner {

    private final ZtCoreService ztCoreService;

    public StartConfig(ZtCoreService ztCoreService) {
        this.ztCoreService = ztCoreService;
    }

    @Override
    public void run(String... args) throws Exception {
        ztCoreService.init();
    }

}
