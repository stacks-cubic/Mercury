package cc.stacks.mercury.config;

import cc.stacks.mercury.data.ConfigData;
import cc.stacks.mercury.model.Config;
import cc.stacks.mercury.util.LogUtil;
import cc.stacks.mercury.util.ServerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unused")
public class SystemConfig implements ServletContextAware {

    @Value("${server.port}")
    private int port;
    @Value("${mercury.init}")
    private Boolean initState;
    private final ConfigData configData;

    private static Map<String, String> configMap;

    public SystemConfig(ConfigData configData) {
        this.configData = configData;
    }

    public static String get(String module, String key) {
        try {
            return configMap.get(key);
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean getBoolean(String module, String key) {
        try {
            if (configMap.get(key).length() == 1) return configMap.get(key).equals("1");
            return configMap.get(key).equals("true");
        } catch (Exception e) {
            return false;
        }
    }

    public static int getInt(String module, String key) {
        try {
            return Integer.parseInt(configMap.get(key));
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(String module, String key) {
        try {
            return Double.parseDouble(configMap.get(key));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static long getLong(String module, String key) {
        try {
            return Long.parseLong(configMap.get(key));
        } catch (Exception e) {
            return 0L;
        }
    }

    public static void loadConfig(List<Config> data) {
        Map<String, String> cacheMap = new HashMap<>();
        if (data != null) for (Config config : data) {
            cacheMap.put(config.getKey(), config.getValue());
        }
        configMap = cacheMap;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        try {
            if (initState) {
                List<Config> data = configData.selectList();
                if (data != null && !data.isEmpty()) {
                    SystemConfig.loadConfig(data);
                    LogUtil.info(data.size() + " config items loaded");
                } else LogUtil.warn("No config available");
            } else {
                StringBuilder tips = new StringBuilder("Not yet initialized\n========================================\n");
                tips.append("Please complete the initial config first\n");
                List<String> ipv4List = ServerUtil.getIpv4Addresses();
                for (String ip : ipv4List){
                    tips.append("-> http://").append(ip).append(":").append(port).append("/install\n");
                }
                tips.append("========================================");
                LogUtil.warn(tips.toString());
                configMap = new HashMap<>();
            }
        } catch (Exception e) {
            LogUtil.error(10000, "Failed to load config");
        }
    }

}
