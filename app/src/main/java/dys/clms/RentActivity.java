package dys.clms;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.db.CustomerClassify;
import dys.clms.bean.db.Pact;
import dys.clms.bean.db.Repertory;

/**
 * Created by Administrator on 2016/6/16 0016.
 * 出租电脑
 */
public class RentActivity extends BaseActivity {

    private static final int REQUEST_CODE = 2333;
    @BindView(R.id.et_repertory_id)
    EditText etRepertoryId;
    @BindView(R.id.btn_search_repertory)
    Button btnSearchRepertory;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_user_address)
    EditText etUserAddress;
    @BindView(R.id.et_rent)
    EditText etRent;
    @BindView(R.id.et_deposit)
    EditText etDeposit;

    @BindView(R.id.et_signed_at)
    EditText etSignedAt;
    @BindView(R.id.btn_build_pact)
    Button btnBuildPact;
    @BindView(R.id.sp_customer_classify)
    Spinner spCustomerClassify;
    List<String> customerClassify = new ArrayList<>();
    @BindView(R.id.tv_rent_time)
    TextView tvRentTime;
    @BindView(R.id.tv_over_time)
    TextView tvOverTime;

    private int isPactRelet; //是否是续约
    private List<Pact> mPactList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        ButterKnife.bind(this);
        isPactRelet = getIntent().getIntExtra("pact_relet", -1);

        List<CustomerClassify> customers = DataSupport.findAll(CustomerClassify.class);
        if (isPactRelet == 1) {
            String repe_id = getIntent().getStringExtra("repe_id");
            String name = getIntent().getStringExtra("customer_name");
            mPactList = DataSupport.where("repe_id=? and customer_name=?", repe_id, name).find(Pact.class);
            customerClassify.add(mPactList.get(0).getCustomer_classify());
            etRepertoryId.setText(mPactList.get(0).getRepe_id());
            btnSearchRepertory.setEnabled(false);
            etUserName.setText(mPactList.get(0).getCustomer_name());
            etTel.setText(mPactList.get(0).getCustomer_tel());
            etIdCard.setText(mPactList.get(0).getCustomer_id_card());
            etUserAddress.setText(mPactList.get(0).getCustomer_address());
            etRent.setText(String.valueOf(mPactList.get(0).getRent()));
            etDeposit.setText(String.valueOf(mPactList.get(0).getDeposit()));
            etSignedAt.setText(mPactList.get(0).getRent_address());
            tvRentTime.setText(mPactList.get(0).getBegin_time());
            tvOverTime.setText(mPactList.get(0).getEnd_time());
        }
        for (int i = 0; i < customers.size(); i++) {
            customerClassify.add(customers.get(i).getName());
        }
        spCustomerClassify.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                customerClassify));
    }

    @Override
    protected void initTitle() {
        setTitle("出租电脑");
    }

    @OnClick({R.id.btn_search_repertory, R.id.btn_build_pact, R.id.tv_rent_time, R.id.tv_over_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search_repertory:
                startActivityForResult(new Intent(mContext, RepertoryActivity.class)
                        .putExtra("repe_search", 1), REQUEST_CODE);
                break;
            case R.id.btn_build_pact:
                boolean isSave = false;
                List<Pact> tmpPacts = DataSupport.where("repe_id=? and customer_name=?",
                        etRepertoryId.getText().toString(),
                        etUserName.getText().toString()).find(Pact.class);
                if (TextUtils.isEmpty(etUserName.getText().toString())
                        || TextUtils.isEmpty(etTel.getText().toString())
                        || TextUtils.isEmpty(etRepertoryId.getText().toString())
                        || TextUtils.isEmpty(etRent.getText().toString())
                        || TextUtils.isEmpty(tvRentTime.getText().toString())
                        || TextUtils.isEmpty(tvOverTime.getText().toString())) {
                    Toast.makeText(mContext, "信息未完善", Toast.LENGTH_SHORT).show();
                } else if (tmpPacts.size() != 0 && isPactRelet != 1) {
                    Toast.makeText(mContext, "该用户此订单合同已存在,请到合同查询中查找该用户进行续租或者终止合同操作", Toast.LENGTH_LONG).show();
                } else {
                    Pact pact = new Pact();
                    pact.setRepe_id(etRepertoryId.getText().toString());
                    pact.setPact_state("有效");
                    pact.setCustomer_name(etUserName.getText().toString());
                    pact.setCustomer_tel(etTel.getText().toString().trim());
                    pact.setCustomer_id_card(etIdCard.getText().toString().trim());
                    pact.setCustomer_address(etUserAddress.getText().toString().trim());
                    pact.setRent(Float.parseFloat(etRent.getText().toString()));
                    pact.setDeposit(Float.parseFloat(etDeposit.getText().toString()));
                    pact.setRent_address(etSignedAt.getText().toString());
                    pact.setBegin_time(tvRentTime.getText().toString());
                    pact.setEnd_time(tvOverTime.getText().toString());
                    pact.setCustomer_classify(customerClassify.get(spCustomerClassify.getSelectedItemPosition()));
                    if (isPactRelet == 1) {
                        pact.updateAll("repe_id=? and customer_name=?",
                                etRepertoryId.getText().toString(), etUserName.getText().toString());
                    } else {
                        isSave = pact.save();
                        Repertory repertory = new Repertory();
                        repertory.setRent_state("已出租");
                        repertory.setRent(Float.parseFloat(etRent.getText().toString()));
                        repertory.setDeposit(Float.parseFloat(etDeposit.getText().toString()));
                        repertory.updateAll("repe_id=?", etRepertoryId.getText().toString());
                    }
                    if (isSave || isPactRelet == 1) {
                        finish();
                    } else {
                        Toast.makeText(mContext, "合同生成失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.tv_rent_time:
                selectTime(tvRentTime);
                break;
            case R.id.tv_over_time:
                selectTime(tvOverTime);
                break;
        }
    }

    private void selectTime(final TextView textView) {
        new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String repe_id = data.getStringExtra("repe_id");
            String rent = data.getStringExtra("rent");
            String deposit = data.getStringExtra("deposit");
            etRepertoryId.setText(repe_id);
            etRent.setText(rent);
            etDeposit.setText(deposit);
        }
    }

}
