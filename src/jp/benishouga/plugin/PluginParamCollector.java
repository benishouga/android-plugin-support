package jp.benishouga.plugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;

/**
 * プラグインの情報を収集するクラスです。
 *
 * @author benishouga
 */
public class PluginParamCollector {

    private Context context;
    private String pluginAction;

    /**
     * コンストラクタ.
     *
     * @param context
     *            アプリケーションコンテキスト
     * @param pluginAction
     *            対応アプリケーションを抽出するためのAction
     */
    public PluginParamCollector(Context context, String pluginAction) {
        this.context = context;
        this.pluginAction = pluginAction;
    }

    /**
     * プラグインとなりえるアプリケーションを見つけた際に呼び出されるlistenerクラスを設定します。
     */
    public void setOnFindPluginListener(OnFindPluginListener listener) {
        this.onFindPluginlistener = listener;
    }

    /**
     * プラグインアプリケーションとの連携でエラーが発生した際のlistenerクラスを設定します。このリスナーが設定されていない場合、
     * プラグインアプリケーションないでエラーが発生した場合、{@link }PluginErrorException} を throwします。
     */
    public void setOnErrorPluginListener(OnErrorPluginListener listener) {
        this.onErrorPluginListener = listener;
    }

    /**
     * プラグインとなりえるアプリケーションを見つけた際に呼び出されるlistenerクラスです。
     */
    public interface OnFindPluginListener {
        /**
         * プラグインとなりえるアプリケーションを見つけた際に呼び出されます。 返却値として、falseを返すことで対象を回避することができます。
         *
         * @param targetName
         *            {@link ActivityInfo#name}
         * @param packageName
         *            {@link ActivityInfo#packageName}
         * @return 対象を読み込むか否か true の場合、読み込み処理を続行します。 false の場合、読み込み処理を回避します。
         */
        public boolean onFindPlugin(String targetName, String packageName);
    }

    /**
     * プラグインとのアプリケーションとの連携でエラーが発生した際に呼び出されるlistenerクラスです。
     */
    public interface OnErrorPluginListener {
        /**
         * プラグインとのアプリケーションとの連携でエラーが発生した際に呼び出されます。
         */
        public void onErrorPlugin(PluginParamHolder holder, String source, Exception e);
    }

    private OnFindPluginListener onFindPluginlistener = null;
    private OnErrorPluginListener onErrorPluginListener = null;

    /**
     * プラグイン情報を収集します。
     *
     * @param source
     *            プラグインに渡す文字列
     * @return プラグイン情報
     *
     * @throws PluginErrorException
     *             プラグイン情報の収集で例外が発生し、例外をハンドリングするリスナーが登録されていなかった際、この例外が投げられます。
     */
    public List<PluginParamHolder> collectPluginParam(String source) {
        List<PluginParamHolder> list = collectPlugin();

        for (PluginParamHolder holder : list) {
            try {
                queryPlugin(holder, source);
            } catch (Exception e) {
                if (onErrorPluginListener == null) {
                    throw new PluginErrorException(holder.getApplicationName() + " failed.. It is called with " + source, e);
                }
                onErrorPluginListener.onErrorPlugin(holder, source, e);
            }
        }

        return list;
    }

    /**
     * プラグインに接続し、プラグインを呼び出すための情報を取得します。
     *
     * @param holder
     *            プラグインアプリケーションを呼び出すための情報を保持するクラスです。
     * @param source
     *            プラグインアプリケーションに接続した際に渡すパラメータ文字列
     */
    public void queryPlugin(PluginParamHolder holder, String source) {
        final Uri uri = Uri.parse("content://" + holder.getClassName() + "?" + Plugin.QUERY_KEY_SOURCE + "=" + Uri.encode(source));

        long startTime = new Date().getTime();

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

        holder.setTimeSpent(new Date().getTime() - startTime);
    }

    /**
     * プラグイン情報を収集します。
     *
     * @return プラグインの設定に関する情報
     */
    public List<PluginParamHolder> collectPlugin() {
        List<PluginParamHolder> list = new ArrayList<PluginParamHolder>();

        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(new Intent(pluginAction).addCategory(Plugin.CATEGORY_PLAGGABLE), 0);

        for (ResolveInfo info : resolveInfoList) {
            final ActivityInfo activityInfo = info.activityInfo;
            if (onFindPluginlistener != null) {
                if (!onFindPluginlistener.onFindPlugin(activityInfo.name, activityInfo.packageName)) {
                    continue;
                }
            }

            PluginParamHolder holder = new PluginParamHolder(activityInfo.loadLabel(pm).toString(), activityInfo.name, activityInfo.packageName);
            List<ResolveInfo> settingList = pm.queryIntentActivities(new Intent().setClassName(holder.getPackageName(), holder.getSettingClassName()), 0);
            list.add(holder.setExistSettingAcitivity(settingList != null && settingList.size() > 0));
        }
        return list;
    }
}
