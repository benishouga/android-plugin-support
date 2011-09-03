package jp.benishouga.plugin.example;

import jp.benishouga.plugin.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExamplePluginSettings extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ViewGroup main = (ViewGroup) findViewById(R.id.main);
        TextView text = new TextView(this);
        text.setText("ExamplePluginSettings!!");
        main.addView(text);
    }
}