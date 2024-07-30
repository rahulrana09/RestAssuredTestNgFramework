package framework;


import framework.globalConstants.FilePath;

import java.time.Duration;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader(FilePath.basePath + "/src/test/resources/automation.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getClientId() {
        return getProp("client_id");
    }

    public String getClientSecret() {
        return getProp("client_secret");
    }

    public String getGrantType() {
        return getProp("grant_type");
    }

    public String getRefreshToken() {
        return getProp("refresh_token");
    }

    public String getBrowserType() {
        return getProp("browser_name");
    }

    public Duration getImplicitWait() {
        return Duration.ofSeconds(Long.parseLong(getProp("implicit_wait")));
    }

    public Duration getExplicitWait() {
        return Duration.ofSeconds(Long.parseLong(getProp("explicit_wait")));
    }

    public Duration getExplicitSmallWait() {
        return Duration.ofSeconds(Long.parseLong(getProp("explicit_small_wait")));
    }

    public Duration getExplicitLongWait() {
        return Duration.ofSeconds(Long.parseLong(getProp("explicit_long_wait")));
    }

    public long getStepWaitTime() {
        return Long.parseLong(getProp("step_wait_time"));
    }

    public String getURL() {
        return getProp("ui_url");
    }

    public String getStandardUserName() {
        return getProp("standard_username");
    }

    public String getStandardUserPwd() {
        return getProp("standard_password");
    }


    private String getProp(String attribute) {
        String prop = properties.getProperty(attribute);
        if (prop != null) return prop;
        else throw new RuntimeException("Property " + attribute + " is not specified in automation.properties");
    }

}
