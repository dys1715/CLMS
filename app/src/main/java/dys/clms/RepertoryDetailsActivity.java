package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dys on 2016/6/16 0016.
 * 库存详情页  (新增、修改、删除)
 */
public class RepertoryDetailsActivity extends BaseActivity {

    private int dataType;
    @BindView(R.id.et_id) EditText mEtId;
    @BindView(R.id.sp_state) Spinner mSpState;
    @BindView(R.id.sp_cpu) Spinner mSpCpu;
    @BindView(R.id.sp_mainboard) Spinner mSpMainboard;
    @BindView(R.id.sp_memory) Spinner mSpMemory;
    @BindView(R.id.sp_hard_disk) Spinner mSpHardDisk;
    @BindView(R.id.sp_gpu) Spinner mSpGpu;
    @BindView(R.id.sp_screen) Spinner mSpScreen;
    @BindView(R.id.sp_cd_driver) Spinner mSpCdDriver;
    @BindView(R.id.sp_floppy_drive) Spinner mSpFloppyDrive;
    @BindView(R.id.sp_sound_card) Spinner mSpSoundCard;
    @BindView(R.id.sp_keyboard) Spinner mSpKeyboard;
    @BindView(R.id.sp_mouse) Spinner mSpMouse;
    @BindView(R.id.sp_box) Spinner mSpBox;
    @BindView(R.id.sp_voice_box) Spinner mSpVoiceBox;
    @BindView(R.id.sp_network_card) Spinner mSpNetworkCard;
    @BindView(R.id.et_rent) EditText mEtRent;
    @BindView(R.id.et_deposit) EditText mEtDeposit;
    @BindView(R.id.sp_classify) Spinner mSpClassify;
    @BindView(R.id.et_remark) EditText mEtRemark;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;
    @OnClick(R.id.btn_confirm)
    public void onClick() {
        Toast.makeText(mContext,"save",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repe_details);
        ButterKnife.bind(this);
        dataType = getIntent().getIntExtra("dataType", -1);
        switch (dataType) {
            //change
            case 1:
                break;
            //view
            case 2:
                setAllViewUnClick();
                mBtnConfirm.setVisibility(View.INVISIBLE);
                break;
            //add
            case 3:
                break;
            default:
                break;
        }
    }

    private void setAllViewUnClick() {
        mEtId.setEnabled(false);
        mEtRent.setEnabled(false);
        mEtDeposit.setEnabled(false);
        mEtRemark.setEnabled(false);
        mEtId.setFocusable(false);
        mEtRent.setFocusable(false);
        mEtDeposit.setFocusable(false);
        mEtRemark.setFocusable(false);
        mSpState.setClickable(false);
        mSpCpu.setClickable(false);
        mSpMainboard.setClickable(false);
        mSpMemory.setClickable(false);
        mSpHardDisk.setClickable(false);
        mSpGpu.setClickable(false);
        mSpScreen.setClickable(false);
        mSpCdDriver.setClickable(false);
        mSpFloppyDrive.setClickable(false);
        mSpSoundCard.setClickable(false);
        mSpKeyboard.setClickable(false);
        mSpMouse.setClickable(false);
        mSpBox.setClickable(false);
        mSpVoiceBox.setClickable(false);
        mSpNetworkCard.setClickable(false);
        mSpClassify.setClickable(false);
    }

    @Override
    protected void initTitle() {
        setTitle("订单详情");
    }
}
