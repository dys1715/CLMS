package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/6/16 0016.
 * 出租电脑
 */
public class RentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
    }

    @Override
    protected void initTitle() {
        setTitle("出租电脑");
    }
}