package jp.benishouga.plugin;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Pluginの起動パラメータを作成し、Pluggableなアプリケーションに返却する{@link ContentProvider}です。
 *
 * @author benishouga
 */
public abstract class AbstractPluginProvider extends ContentProvider {

    /**
     * Pluginの起動パラメータを作成します。
     *
     * @param source
     * @return Pluginの起動パラメータのリスト
     */
    protected abstract List<PluginParam> createPluginParams(String source);

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return new PluginCursor(createPluginParams(uri.getQueryParameter("source")));
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

}
