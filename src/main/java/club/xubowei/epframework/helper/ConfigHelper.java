package club.xubowei.epframework.helper;

import club.xubowei.epframework.constant.ConfigConstant;
import club.xubowei.epframework.constant.ConfigConstantDefaultValue;
import club.xubowei.epframework.util.PropsUtil;

import java.util.Properties;

/**
 * @author zhisheng
 * @date 2018/7/28.
 */
public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getConfigByName(String configName) {
        return PropsUtil.getString(CONFIG_PROPS, configName);
    }

    public static String getConfigByName(String configName, String defaultValue) {
        return PropsUtil.getString(CONFIG_PROPS, configName, defaultValue);
    }

    public static String getAppJspPath() {
        return getConfigByName(ConfigConstant.APP_JSP_PATH, ConfigConstantDefaultValue.APP_JSP_PATH);
    }

    public static String getAppAssetPath() {
        return getConfigByName(ConfigConstant.APP_ASSET_PATH, ConfigConstantDefaultValue.APP_ASSET_PATH);
    }


}
