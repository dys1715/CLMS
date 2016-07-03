package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.db.Pact;

/**
 * Created by dys on 2016/6/17 0017.
 * 合同详情
 */
public class PactDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_pact_id)
    TextView tvPactId;
    @BindView(R.id.tv_pact_state)
    TextView tvPactState;
    @BindView(R.id.tv_pact_customer)
    TextView tvPactCustomer;
    @BindView(R.id.tv_pact_customer_tel)
    TextView tvPactCustomerTel;
    @BindView(R.id.tv_pact_customer_address)
    TextView tvPactCustomerAddress;
    @BindView(R.id.tv_pact_customer_classify)
    TextView tvPactCustomerClassify;
    @BindView(R.id.tv_pact_id_card)
    TextView tvPactIdCard;
    @BindView(R.id.tv_pact_address)
    TextView tvPactAddress;
    @BindView(R.id.tv_pact_time)
    TextView tvPactTime;
    @BindView(R.id.tv_repe_id)
    TextView tvRepeId;
    @BindView(R.id.tv_rent_time)
    TextView tvRentTime;
    @BindView(R.id.tv_rent_over)
    TextView tvRentOver;
    @BindView(R.id.tv_rent)
    TextView tvRent;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.btn_pact_preview)
    Button btnPactPreview;
    @BindView(R.id.btn_pact_relet)
    Button btnPactRelet;
    @BindView(R.id.btn_pact_over)
    Button btnPactOver;
    @BindView(R.id.btn_pact_print)
    Button btnPactPrint;

    private List<Pact> pactList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_details);
        ButterKnife.bind(this);
        pactList = DataSupport.where("repe_id=?", getIntent().getStringExtra("repe_id")).find(Pact.class);
        tvPactId.setText("库存编号：" + pactList.get(0).getRepe_id());
        tvPactState.setText("合同状态：" + pactList.get(0).getPact_state());
        tvPactCustomer.setText("乙方姓名：" + pactList.get(0).getCustomer_name());
        tvPactCustomerTel.setText("乙方电话：" + pactList.get(0).getCustomer_tel());
        tvPactCustomerAddress.setText("乙方地址：" + pactList.get(0).getCustomer_address());
        tvPactCustomerClassify.setText("客户类别：" + pactList.get(0).getCustomer_classify());
        tvPactIdCard.setText("身份证：" + pactList.get(0).getCustomer_id_card());
        tvPactAddress.setText("签订地点：" + pactList.get(0).getRent_address());
        tvRepeId.setVisibility(View.GONE);
        tvPactTime.setVisibility(View.GONE);
        tvRentTime.setText("出租日期：" + pactList.get(0).getBegin_time());
        tvRentOver.setText("截止日期：" + pactList.get(0).getEnd_time());
        tvRent.setText("租金：" + pactList.get(0).getRent());
        tvDeposit.setText("押金：" + pactList.get(0).getDeposit());
    }

    @Override
    protected void initTitle() {
        setTitle("合同详情");
    }

    @OnClick({R.id.btn_pact_relet, R.id.btn_pact_over})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pact_relet:
                break;
            case R.id.btn_pact_over:
                break;
        }
    }
}
