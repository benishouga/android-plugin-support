package jp.benishouga.plugin.example;

import java.util.List;

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

        PluginParamCollector collector = new PluginParamCollector(this, "jp.benishouga.plugin.example.ACTION_PICK_PLUGIN");
        collector.setOnFindPluginListener(new OnFindPluginListener() {
            @Override
            public boolean onFindPlugin(String targetName, String packageName) {
                Log.d("Pluggable", "onFindPlugin targetName: " + targetName + ", packageName: " + packageName);

                // プラグインの無効化等がされていて、読み込んだアプリを読み込ませたくない場合、ここでfalseを返す。

                return true;
            }
        });

        // エラーが発生した場合のリスナーを登録する。これが登録されていないと、
        // プラグインアプリで例外が発生した場合、それがプラガブルなアプリに波及してしまう。
        collector.setOnErrorPluginListener(new OnErrorPluginListener() {
            @Override
            public void onErrorPlugin(PluginParamHolder holder, String source, Exception e) {
                Log.e("Pluggable", "onErrorPlugin applicationName: " + holder.getApplicationName() + ", source: " + source, e);

                // エラーが発生した場合の処理を書く
            }
        });

        List<PluginParamHolder> list = collector.collectPluginParam("http://www.yahoo.co.jp");

        ViewGroup main = (ViewGroup) findViewById(R.id.main);
        for (final PluginParamHolder holder : list) {
            Log.d("Pluggable", "holder.className: " + holder.getClassName() + ", holder.packageName: " + holder.getPackageName());

            for (PluginParam param : holder) {
                Log.d("Pluggable", "param.text: " + param.getText());
                Button button = (Button) this.getLayoutInflater().inflate(R.layout.button, null);
                button.setText(param.getText());
                button.setOnClickListener(new IntentOnClickListener(param.createIntent()));
                main.addView(button);

            }
            
            Button settings = (Button) this.getLayoutInflater().inflate(R.layout.button, null);
            settings.setText(holder.getApplicationName() + "の設定画面を呼び出す");
            settings.setOnClickListener(new IntentOnClickListener(holder.createSettingsIntent()));
            main.addView(settings);
        }
    }

    private class IntentOnClickListener implements OnClickListener {

        private Intent intent;

        public IntentOnClickListener(Intent intent) {
            this.intent = intent;
        }

        @Override
        public void onClick(View v) {
            startActivity(this.intent);
        }
    }
}
