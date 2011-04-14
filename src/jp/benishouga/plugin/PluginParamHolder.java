package jp.benishouga.plugin;

import java.util.ArrayList;

public class PluginParamHolder extends ArrayList<PluginParam> {
    private static final long serialVersionUID = 1L;

    private String className;
    private String packageName;

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

}
