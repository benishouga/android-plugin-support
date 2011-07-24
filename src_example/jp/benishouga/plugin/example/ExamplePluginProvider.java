package jp.benishouga.plugin.example;

import java.util.ArrayList;
import java.util.List;

import jp.benishouga.plugin.AbstractPluginProvider;
import jp.benishouga.plugin.MethodIcon;
import jp.benishouga.plugin.PluginParam;
import android.content.Intent;

public class ExamplePluginProvider extends AbstractPluginProvider {
    @Override
    protected List<PluginParam> createPluginParams(String source) {
        List<PluginParam> params = new ArrayList<PluginParam>();
        PluginParam param = new PluginParam();
        param.setData("http://www.google.com");
        param.setAction(Intent.ACTION_VIEW);
        param.setClickable(true);
        param.setText("表示文字列");
        param.setMethodIcon(MethodIcon.Memo);
        params.add(param);
        return params;
    }

}
