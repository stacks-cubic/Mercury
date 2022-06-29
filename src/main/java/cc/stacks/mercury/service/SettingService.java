package cc.stacks.mercury.service;

import cc.stacks.mercury.config.SystemConfig;
import cc.stacks.mercury.data.ConfigData;
import cc.stacks.mercury.model.setting.P1;
import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettingService {

    private final ConfigData configData;

    public SettingService(ConfigData configData) {
        this.configData = configData;
    }

    public boolean updateP1(P1 input) {
        try {
            Map<String, Object> data = JSON.parseObject(JSON.toJSONString(input));
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = "page:theme:" + entry.getKey();
                if ((entry.getValue() instanceof String && !SystemConfig.get(key).equals(entry.getValue())) ||
                        (entry.getValue() instanceof Boolean && SystemConfig.getBoolean(key) != (Boolean) entry.getValue()) ||
                        (entry.getValue() instanceof Integer && SystemConfig.getInt(key) != (Integer) entry.getValue()))
                    configData.update(key, String.valueOf(entry.getValue()));
            }
            SystemConfig.loadConfig(configData.selectList());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}