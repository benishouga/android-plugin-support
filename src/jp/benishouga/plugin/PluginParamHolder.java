package jp.benishouga.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;

/**
 * プラグイン拡張可能なアプリケーションとプラグインアプリケーション間でデータを受け渡すためクラスです。
 *
 * @author benishouga
 */
public class PluginParamHolder implements Iterable<PluginParam> {
    private static final String SETTING_CLASS_NAME_SUFFIX = "Settings";

    private String applicationName;
    private String className;
    private String packageName;
    private boolean existSettingActivity;
    private long timeSpent = 0;
    private List<PluginParam> plugins;

    public PluginParamHolder(String applicationName, String className, String packageName) {
        this.applicationName = applicationName;
        this.className = className;
        this.packageName = packageName;
        this.plugins = new ArrayList<PluginParam>();
    }

    PluginParam create() {
        PluginParam param = new PluginParam(this);
        this.plugins.add(param);
        return param;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getSettingClassName() {
        return getClassName() + SETTING_CLASS_NAME_SUFFIX;
    }

    PluginParamHolder setExistSettingAcitivity(boolean existSettingActivity) {
        this.existSettingActivity = existSettingActivity;
        return this;
    }

    public boolean existSettingActivity() {
        return existSettingActivity;
    }

    PluginParamHolder setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
        return this;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public String getApplicationName() {
        return applicationName;
    }

    @Override
    public Iterator<PluginParam> iterator() {
        return this.plugins.iterator();
    }

    public Intent createSettingsIntent() {
        return new Intent().setClassName(this.getPackageName(), this.getSettingClassName());
    }
}
