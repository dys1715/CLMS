package dys.clms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                startActivity(new Intent(mContext, ConfigurationsActivity.class));
                break;
            case R.id.btn_renter_info:
                showRenterInfo();
                break;
//            case R.id.btn_address:
//                break;
            case R.id.btn_repe:
                startActivity(new Intent(mContext, SettingRepeClassifyActivity.class));
                break;
            case R.id.btn_customer:
                startActivity(new Intent(mContext, SettingCustomerClassify.class));
                break;
        }
    }

    private void showRenterInfo() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_renter_info, null);
        final EditText etCompany = (EditText) view.findViewById(R.id.et_company);
        final EditText etAddress = (EditText) view.findViewById(R.id.et_address);
        final EditText etTel = (EditText) view.findViewById(R.id.et_tel);
        new AlertDialog.Builder(mContext)
                .setTitle("甲方信息")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(mContext, etCompany.getText().toString(), Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                })
                .show();
    }
}
