package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initTitle() {
        setTitle("设置");
    }
}
