package cc.stacks.mercury.config;

import cc.stacks.mercury.service.ZerotierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartConfig implements CommandLineRunner {

    private final ZerotierService zerotierService;

    public StartConfig(ZerotierService zerotierService) {
        this.zerotierService = zerotierService;
    }

    @Override
    public void run(String... args) throws Exception {
        zerotierService.init();
    }

}
