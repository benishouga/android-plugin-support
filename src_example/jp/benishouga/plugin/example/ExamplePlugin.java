package jp.benishouga.plugin.example;

import jp.benishouga.plugin.Plugin;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class ExamplePlugin extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getCategories().contains(Plugin.CATEGORY_PLAGGABLE)) {
            Intent intent = new Intent();
            intent.setAction(getIntent().getExtras().getString(Plugin.EXTRAS_ACTION));
            intent.setDataAndType(Uri.parse(getIntent().getExtras().getString(Plugin.EXTRAS_DATA)), getIntent().getExtras().getString(Plugin.EXTRAS_TYPE));
            startActivity(intent);
        }
        finish();
    }
}