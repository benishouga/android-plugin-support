package jp.benishouga.plugin;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;

public class PluginParamCollector {

    private Context context;
    private String pluginAction;

    public PluginParamCollector(Context context, String pluginAction) {
        this.context = context;
        this.pluginAction = pluginAction;
    }

    /**
     * プラグインとなりえるアプリケーションを見つけた際に呼び出されるlistenerクラスを設定します。
     */
    public void setListener(OnFindPluginListener listener) {
        this.listener = listener;
    }

    /**
     * プラグインとなりえるアプリケーションを見つけた際に呼び出されるlistenerクラスです。
     *
     * @author benishouga
     */
    public interface OnFindPluginListener {
        /**
         * プラグインとなりえるアプリケーションを見つけた際に呼び出されます。 返却値として、falseを返すことで対象を回避することができます。
         *
         * @param targetName
         *            {@link ActivityInfo#name}
         * @return 対象を読み込むか否か true の場合、読み込み処理を続行します。 false の場合、読み込み処理を回避します。
         */
        public boolean onFindPlugin(String targetName);
    }

    private OnFindPluginListener listener = null;

    /**
     * プラグイン情報を収集します。
     *
     * @param context
     *            コンテキスト
     * @param pluginAction
     *            プラグインを探すためのAction文字列
     * @param source
     *            プラグインに渡す文字列
     * @return プラグイン情報
     */
    public List<PluginParamHolder> collectPluginParam(String source) {
        List<PluginParamHolder> list = new ArrayList<PluginParamHolder>();

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentActivities(new Intent(pluginAction), PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo info : resolveInfo) {

            if (listener != null) {
                if (!listener.onFindPlugin(info.activityInfo.name)) {
                    continue;
                }
            }

            PluginParamHolder holder = new PluginParamHolder().setClassName(info.activityInfo.name).setPackageName(info.activityInfo.packageName);

            Uri uri = Uri.parse("content://" + info.activityInfo.name + "?" + Plugin.QUERY_KEY_SOURCE + "=" + Uri.encode(source));
            final Cursor cur = context.getContentResolver().query(uri, null, null, null, null);
            if (cur != null) {
                try {
                    while (cur.moveToNext()) {
                        holder.add(PluginCursor.revert(cur));
                    }
                } finally {
                    cur.close();
                }
            }
            list.add(holder);
        }

        return list;
    }
}
