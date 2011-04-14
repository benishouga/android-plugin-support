package jp.benishouga.plugin;

public class Plugin {
    /** {@link PluginParam#getAction()} にて設定した値を取得する。 */
    public static final String EXTRAS_ACTION = "jp.benishouga.plugin.extras.ACTION";
    /** {@link PluginParam#getData()} にて設定した値を取得する。 */
    public static final String EXTRAS_DATA = "jp.benishouga.plugin.extras.DATA";
    /** {@link PluginParam#getType()} にて設定した値を取得する。 */
    public static final String EXTRAS_TYPE = "jp.benishouga.plugin.extras.TYPE";

    /** プラグイン機能を有するアプリケーションを対象とするカテゴリ。 */
    public static final String CATEGORY_PLAGGABLE = "jp.benishouga.plugin.category.PLAGGABLE";

    /**
     * {@link AbstractPluginProvider}
     * を呼び出したUriからプラグイン起動情報のデータ取得元となるUri文字列を取得するためのクエリストリングキー。
     */
    public static final String QUERY_KEY_SOURCE = "source";
}
