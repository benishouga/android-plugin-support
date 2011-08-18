package jp.benishouga.plugin.example;

import java.util.List;

import jp.benishouga.plugin.Plugin;
import jp.benishouga.plugin.PluginParam;
import jp.benishouga.plugin.PluginParamCollector;
import jp.benishouga.plugin.PluginParamCollector.OnErrorPluginListener;
import jp.benishouga.plugin.PluginParamCollector.OnFindPluginListener;
import jp.benishouga.plugin.PluginParamHolder;
import jp.benishouga.plugin.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ExamplePluggableActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PluginParamCollector collector = new PluginParamCollector(this, "jp.benishouga.lettuce.ACTION_PLUGIN");
        collector.setOnFindPluginListener(new OnFindPluginListener() {
            @Override
            public boolean onFindPlugin(String targetName, String packageName) {
                Log.d("Pluggable", "onFindPlugin targetName: " + targetName + ", packageName: " + packageName);
                return true;
            }
        });

        collector.setOnErrorPluginListener(new OnErrorPluginListener() {
            @Override
            public void onErrorPlugin(PluginParamHolder holder, String source, Exception e) {
                Log.e("Pluggable", "applicationName: " + holder.getApplicationName() + ", source: " + source, e);
            }
        });
        List<PluginParamHolder> list = collector.collectPluginParam("http://www.yahoo.co.jp");

        for (PluginParamHolder holder : list) {
            Log.d("Pluggable", "holder.className: " + holder.getClassName() + ", holder.packageName: " + holder.getPackageName());

            for (PluginParam param : holder) {
                Log.d("Pluggable", "param.text: " + param.getText());
                ViewGroup main = (ViewGroup) findViewById(R.id.main);
                Button button = (Button) this.getLayoutInflater().inflate(R.layout.button, null);
                button.setText(param.getText());
                button.setOnClickListener(new PluginOnClickListener(holder.getPackageName(), holder.getClassName(), param));
                main.addView(button);
            }
        }
    }

    private class PluginOnClickListener implements OnClickListener {

        private String packageName;
        private String name;
        private PluginParam pluginParam;

        public PluginOnClickListener(String packageName, String name, PluginParam pluginParam) {
            this.packageName = packageName;
            this.name = name;
            this.pluginParam = pluginParam;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();

            intent.addCategory(Plugin.CATEGORY_PLAGGABLE).setClassName(packageName, name);
            intent.putExtra(Plugin.EXTRAS_ACTION, pluginParam.getAction());
            intent.putExtra(Plugin.EXTRAS_DATA, pluginParam.getData());
            intent.putExtra(Plugin.EXTRAS_TYPE, pluginParam.getType());

            startActivity(intent);
        }
    }
}
