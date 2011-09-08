package jp.benishouga.plugin;

import java.io.Serializable;

import android.content.Intent;

public class PluginParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private PluginParamHolder holder = null;
    private String action;
    private String data;
    private String text;
    private String type;
    private MethodIcon methodIcon;
    private boolean isClickable;

    public PluginParam() {
    }

    PluginParam(PluginParamHolder holder) {
        this.holder = holder;
    }

    public String getAction() {
        return action;
    }

    public PluginParam setAction(String action) {
        this.action = action;
        return this;
    }

    public String getData() {
        return data;
    }

    public PluginParam setData(String data) {
        this.data = data;
        return this;
    }

    public String getText() {
        return text;
    }

    public PluginParam setText(String text) {
        this.text = text;
        return this;
    }

    public Boolean isClickable() {
        return isClickable;
    }

    public PluginParam setClickable(boolean isClickable) {
        this.isClickable = isClickable;
        return this;
    }

    public MethodIcon getMethodIcon() {
        return methodIcon;
    }

    public PluginParam setMethodIcon(MethodIcon methodIcon) {
        this.methodIcon = methodIcon;
        return this;
    }

    public String getType() {
        return type;
    }

    public PluginParam setType(String type) {
        this.type = type;
        return this;
    }

    public Intent createIntent() {
        if (holder == null) {
            throw new IllegalAccessError("このメソッドはプラグインアプリから取得したインスタンスの場合のみ利用可能です。");
        }

        Intent intent = new Intent();

        intent.addCategory(Plugin.CATEGORY_PLAGGABLE).setClassName(holder.getPackageName(), holder.getClassName());
        intent.putExtra(Plugin.EXTRAS_ACTION, this.getAction());
        intent.putExtra(Plugin.EXTRAS_DATA, this.getData());
        intent.putExtra(Plugin.EXTRAS_TYPE, this.getType());

        return intent;
    }

}
