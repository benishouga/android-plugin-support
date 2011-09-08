package jp.benishouga.plugin;

import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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
        return new PluginCursor(createPluginParams(uri.getQueryParameter(Plugin.QUERY_KEY_SOURCE)));
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

    static class PluginCursor extends AbstractCursor {

        public static final String _ID = "_id";
        public static final String ACTION = "action";
        public static final String DATA = "url";
        public static final String TEXT = "text";
        public static final String TYPE = "type";
        public static final String METHOD_ICON = "method_icon";
        public static final String IS_CLICKABLE = "is_clickable";
        public static final String _COUNT = "_count";

        private static final int _ID_INDEX = 0;
        private static final int ACTION_INDEX = _ID_INDEX + 1;
        private static final int DATA_INDEX = ACTION_INDEX + 1;
        private static final int TEXT_INDEX = DATA_INDEX + 1;
        private static final int TYPE_INDEX = TEXT_INDEX + 1;
        private static final int METHOD_ICON_INDEX = TYPE_INDEX + 1;
        private static final int IS_CLICKABLE_INDEX = METHOD_ICON_INDEX + 1;
        private static final int _COUNT_INDEX = IS_CLICKABLE_INDEX + 1;

        private final String[] columnNames = new String[] { _ID, ACTION, DATA, TEXT, TYPE, METHOD_ICON, IS_CLICKABLE, _COUNT };
        private final int columnCount = columnNames.length;
        private List<PluginParam> pluginParams;
        private int rowCount = 0;

        public PluginCursor(List<PluginParam> pluginParams) {
            this.pluginParams = pluginParams;
            this.rowCount = pluginParams.size();
        }

        private Object getValue(int column, PluginParam pluginParam) {
            switch (column) {
            case _ID_INDEX:
                return mPos;
            case ACTION_INDEX:
                return pluginParam.getAction();
            case DATA_INDEX:
                return pluginParam.getData();
            case TEXT_INDEX:
                return pluginParam.getText();
            case TYPE_INDEX:
                return pluginParam.getType();
            case METHOD_ICON_INDEX:
                MethodIcon icon = pluginParam.getMethodIcon();
                return icon == null ? null : icon.name();
            case IS_CLICKABLE_INDEX:
                return pluginParam.isClickable();
            case _COUNT_INDEX:
                return pluginParams.size();
            }
            return null;
        }

        private Object get(int column) {
            if (column < 0 || column >= columnCount) {
                throw new CursorIndexOutOfBoundsException("Requested column: " + column + ", # of columns: " + columnCount);
            }
            if (mPos < 0) {
                throw new CursorIndexOutOfBoundsException("Before first row.");
            }
            if (mPos >= rowCount) {
                throw new CursorIndexOutOfBoundsException("After last row.");
            }
            return getValue(column, pluginParams.get(mPos));
        }

        // AbstractCursor implementation.

        @Override
        public int getCount() {
            return rowCount;
        }

        @Override
        public String[] getColumnNames() {
            return columnNames;
        }

        @Override
        public String getString(int column) {
            Object value = get(column);
            return value == null ? null : value.toString();
        }

        @Override
        public short getShort(int column) {
            Object value = get(column);
            return value == null ? 0 : Short.parseShort(value.toString());
        }

        @Override
        public int getInt(int column) {
            Object value = get(column);
            return value == null ? 0 : Integer.parseInt(value.toString());
        }

        @Override
        public long getLong(int column) {
            Object value = get(column);
            return value == null ? 0 : Long.parseLong(value.toString());
        }

        @Override
        public float getFloat(int column) {
            Object value = get(column);
            return value == null ? 0.0f : Float.parseFloat(value.toString());
        }

        @Override
        public double getDouble(int column) {
            Object value = get(column);
            return value == null ? 0.0d : Double.parseDouble(value.toString());
        }

        @Override
        public boolean isNull(int column) {
            return get(column) == null;
        }

        /**
         * {@link Cursor} から {@link PluginParam} にデータを流し込みます。
         *
         * @param cursor
         *            作成元データを示す {@link Cursor} オブジェクト
         * @param param
         *            注入先の{@link PluginParam}
         */
        public static void extract(Cursor cursor, PluginParam param) {
            param.setAction(cursor.getString(ACTION_INDEX)).setClickable(Boolean.valueOf(cursor.getString(IS_CLICKABLE_INDEX)))
                    .setText(cursor.getString(TEXT_INDEX)).setType(cursor.getString(TYPE_INDEX)).setData(cursor.getString(DATA_INDEX));

            String iconString = cursor.getString(METHOD_ICON_INDEX);
            if (iconString != null) {
                param.setMethodIcon(MethodIcon.valueOf(iconString));
            }
        }

    }

}
