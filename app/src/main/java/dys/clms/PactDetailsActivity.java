package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by dys on 2016/6/17 0017.
 * 合同详情
 */
public class PactDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_details);
    }

    @Override
    protected void initTitle() {
        setTitle("合同详情");
    }
}
