package dys.clms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.Company;
import dys.clms.bean.db.CompanyInfo;

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

    EditText etCompany;
    EditText etAddress;
    EditText etTel;
    View view;

    private ArrayList<Company> mCompanyList = new ArrayList<>();

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
        getCompanyDatasFromDB("companyinfo");
        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_renter_info, null);
        etCompany = (EditText) view.findViewById(R.id.et_company);
        etAddress = (EditText) view.findViewById(R.id.et_address);
        etTel = (EditText) view.findViewById(R.id.et_tel);
        new AlertDialog.Builder(mContext)
                .setTitle("甲方信息")
                .setView(view)
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(etCompany.getText().toString())
                                || TextUtils.isEmpty(etAddress.getText().toString())
                                || TextUtils.isEmpty(etTel.getText().toString())) {
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialog, false);//true表示要关闭
                                Toast.makeText(mContext, "您还有未填入的信息，请完善", Toast.LENGTH_SHORT).show();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                                field.setAccessible(true);
                                field.set(dialog, true);//true表示要关闭
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            DataSupport.deleteAll(CompanyInfo.class);
                            CompanyInfo companyInfo = new CompanyInfo();
                            companyInfo.setName(etCompany.getText().toString());
                            companyInfo.setAddress(etAddress.getText().toString());
                            companyInfo.setTel(etTel.getText().toString());
                            companyInfo.save();
                            dialog.cancel();
                        }
                    }
                })
                .show();
    }

    private void getCompanyDatasFromDB(final String tab) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mCompanyList.clear();
//                CompanyInfo info = new CompanyInfo();
//                info.setName(" ");
//                info.setAddress(" ");
//                info.setTel(" ");
//                info.save();
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase()
                            .rawQuery("select * from " + tab + " order by id", null);
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String tel = cursor.getString(cursor.getColumnIndex("tel"));
                            String address = cursor.getString(cursor.getColumnIndex("address"));
                            mCompanyList.add(new Company(name, tel, address));
                        } while (cursor.moveToNext());
                    }else {
                        mCompanyList.add(new Company("", "", ""));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mCompanyList.size()>0){
                                etCompany.setText(mCompanyList.get(0).getName());
                                etAddress.setText(mCompanyList.get(0).getAddress());
                                etTel.setText(mCompanyList.get(0).getTel());
                            }
                        }
                    });
                }
            }
        }).start();
    }
}
