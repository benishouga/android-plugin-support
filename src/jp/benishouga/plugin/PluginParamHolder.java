package jp.benishouga.plugin;

import java.util.ArrayList;

public class PluginParamHolder extends ArrayList<PluginParam> {
    private static final long serialVersionUID = 1L;

    private static final String SETTING_CLASS_NAME_SUFFIX = "Settings";

    private String applicationName;
    private String className;
    private String packageName;
    private boolean existSettingActivity;
    private long timeSpent = 0;

    public PluginParamHolder(String applicationName, String className, String packageName) {
        this.applicationName = applicationName;
        this.className = className;
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public PluginParamHolder setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public PluginParamHolder setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getSettingClassName() {
        return getClassName() + SETTING_CLASS_NAME_SUFFIX;
    }

    public PluginParamHolder setExistSettingAcitivity(boolean existSettingActivity) {
        this.existSettingActivity = existSettingActivity;
        return this;
    }

    public boolean existSettingActivity() {
        return existSettingActivity;
    }

    public PluginParamHolder setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public PluginParamHolder setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
