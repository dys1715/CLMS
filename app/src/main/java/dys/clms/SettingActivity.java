package dys.clms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.btn_configuration)
    Button btnConfiguration;
    @BindView(R.id.btn_renter_info)
    Button btnRenterInfo;
    @BindView(R.id.btn_address)
    Button btnAddress;
    @BindView(R.id.btn_repe)
    Button btnRepe;
    @BindView(R.id.btn_customer)
    Button btnCustomer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    protected void initTitle() {
        setTitle("设置");
    }

    @OnClick({R.id.btn_configuration, R.id.btn_renter_info, R.id.btn_address, R.id.btn_repe, R.id.btn_customer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_configuration:
                startActivity(new Intent(mContext,ConfigurationsActivity.class));
                break;
            case R.id.btn_renter_info:
                break;
            case R.id.btn_address:
                break;
            case R.id.btn_repe:
                break;
            case R.id.btn_customer:
                break;
        }
    }
}
