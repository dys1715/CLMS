package dys.clms;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.db.Repertory;
import dys.clms.common.ConstantValue;

/**
 * Created by dys on 2016/6/16 0016.
 * 库存详情页  (新增、修改、删除)
 */
public class RepertoryDetailsActivity extends BaseActivity {

    @BindView(R.id.et_id)
    EditText mEtId;
    @BindView(R.id.sp_state)
    Spinner mSpState;
    @BindView(R.id.sp_cpu)
    Spinner mSpCpu;
    @BindView(R.id.sp_mainboard)
    Spinner mSpMainboard;
    @BindView(R.id.sp_memory)
    Spinner mSpMemory;
    @BindView(R.id.sp_hard_disk)
    Spinner mSpHardDisk;
    @BindView(R.id.sp_gpu)
    Spinner mSpGpu;
    @BindView(R.id.sp_screen)
    Spinner mSpScreen;
    @BindView(R.id.sp_cd_driver)
    Spinner mSpCdDriver;
    @BindView(R.id.sp_floppy_drive)
    Spinner mSpFloppyDrive;
    @BindView(R.id.sp_sound_card)
    Spinner mSpSoundCard;
    @BindView(R.id.sp_keyboard)
    Spinner mSpKeyboard;
    @BindView(R.id.sp_mouse)
    Spinner mSpMouse;
    @BindView(R.id.sp_box)
    Spinner mSpBox;
    @BindView(R.id.sp_voice_box)
    Spinner mSpVoiceBox;
    @BindView(R.id.sp_network_card)
    Spinner mSpNetworkCard;
    @BindView(R.id.et_rent)
    EditText mEtRent;
    @BindView(R.id.et_deposit)
    EditText mEtDeposit;
    @BindView(R.id.sp_classify)
    Spinner mSpClassify;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private List<String> classifyList;
    private List<String> cpuList;
    private List<String> mainboardList;
    private List<String> memoryList;
    private List<String> hardDiskList;
    private List<String> gpuList;
    private List<String> screenList;
    private List<String> cdDriverList;
    private List<String> softDriverList;
    private List<String> soundCardList;
    private List<String> keyBoardList;
    private List<String> mouseList;
    private List<String> boxList;
    private List<String> soundBoxList;
    private List<String> networkList;
    private int dataType;
    private String repe_id;
    private String repe_classify;
    private String[] rent_state = {"未出租", "已出租"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repe_details);
        ButterKnife.bind(this);

        dataType = getIntent().getIntExtra("dataType", -1);
        List<Repertory> repeList = new ArrayList<>();
        if (dataType != ConstantValue.ADD_DATA) {
            repe_id = getIntent().getStringExtra("repe_id");
            repe_classify = getIntent().getStringExtra("repe_classify");
            repeList = DataSupport
                    .where("repe_id=? and repe_classify_name=?", repe_id, repe_classify)
                    .find(Repertory.class);
        }
        switch (dataType) {
            //change
            case 1:
                mEtId.setEnabled(false);
                classifyList = initList(classifyList, repeList.get(0).getRepe_classify_name());
                cpuList = initList(cpuList, repeList.get(0).getCpu());
                mainboardList = initList(mainboardList, repeList.get(0).getMainboard());
                memoryList = initList(memoryList, repeList.get(0).getMemory());
                hardDiskList = initList(hardDiskList, repeList.get(0).getHardDisk());
                gpuList = initList(gpuList, repeList.get(0).getGpu());
                screenList = initList(screenList, repeList.get(0).getScreen());
                cdDriverList = initList(cdDriverList, repeList.get(0).getCdDriver());
                softDriverList = initList(softDriverList, repeList.get(0).getSoftDriver());
                soundCardList = initList(soundCardList, repeList.get(0).getSoundCard());
                keyBoardList = initList(keyBoardList, repeList.get(0).getKeyboard());
                mouseList = initList(mouseList, repeList.get(0).getMouse());
                boxList = initList(boxList, repeList.get(0).getBox());
                soundBoxList = initList(soundBoxList, repeList.get(0).getSoundBox());
                networkList = initList(networkList, repeList.get(0).getNetworkCard());
                addRepe();
                mEtId.setText(repeList.get(0).getRepe_id());
                if ("已出租".equals(repeList.get(0).getRent_state())) {
                    String[] strings = {"已出租", "未出租"};
                    mSpState.setAdapter(new ArrayAdapter<>(mContext,
                            android.R.layout.simple_spinner_item,
                            strings));
                } else {
                    String[] strings = {"未出租", "已出租"};
                    mSpState.setAdapter(new ArrayAdapter<>(mContext,
                            android.R.layout.simple_spinner_item,
                            strings));
                }
                mEtRent.setText(String.valueOf(repeList.get(0).getRent()));
                mEtDeposit.setText(String.valueOf(repeList.get(0).getDeposit()));
                mEtRemark.setText(repeList.get(0).getRemark());
                break;
            //view
            case 2:
                setAllViewUnClick();
                mBtnConfirm.setVisibility(View.INVISIBLE);
//                List<Repertory> repeList = DataSupport.findAll(Repertory.class);
                initDatas(repeList);
                break;
            //add
            case 3:
                classifyList = initList(classifyList);
                cpuList = initList(cpuList);
                mainboardList = initList(mainboardList);
                memoryList = initList(memoryList);
                hardDiskList = initList(hardDiskList);
                gpuList = initList(gpuList);
                screenList = initList(screenList);
                cdDriverList = initList(cdDriverList);
                softDriverList = initList(softDriverList);
                soundCardList = initList(soundCardList);
                keyBoardList = initList(keyBoardList);
                mouseList = initList(mouseList);
                boxList = initList(boxList);
                soundBoxList = initList(soundBoxList);
                networkList = initList(networkList);
                addRepe();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initTitle() {
        setTitle("货单详情");
    }

    private void initDatas(List<Repertory> repeList) {
        mSpClassify.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getRepe_classify_name()}));
        mEtId.setText(repeList.get(0).getRepe_id());
        mSpState.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getRent_state()}));
        mSpCpu.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getCpu()}));
        mSpMainboard.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getMainboard()}));
        mSpMemory.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getMemory()}));
        mSpHardDisk.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getHardDisk()}));
        mSpGpu.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getGpu()}));
        mSpScreen.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getScreen()}));
        mSpCdDriver.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getCdDriver()}));
        mSpFloppyDrive.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getSoftDriver()}));
        mSpSoundCard.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getSoundCard()}));
        mSpKeyboard.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getKeyboard()}));
        mSpMouse.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getMouse()}));
        mSpBox.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getBox()}));
        mSpVoiceBox.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getSoundBox()}));
        mSpNetworkCard.setAdapter(new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item,
                new String[]{repeList.get(0).getNetworkCard()}));
        mEtRent.setText(String.valueOf(repeList.get(0).getRent()));
        mEtDeposit.setText(String.valueOf(repeList.get(0).getDeposit()));
        mEtRemark.setText(repeList.get(0).getRemark());
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

    @OnClick(R.id.btn_confirm)
    public void onClick() {
        switch (dataType) {
            //change
            case 1:
                if (TextUtils.isEmpty(getSpData(classifyList, mSpClassify))
                        || TextUtils.isEmpty(mEtId.getText().toString().trim())) {
                    Toast.makeText(mContext, "信息未完善", Toast.LENGTH_SHORT).show();
                } else {
                    Repertory repertory = new Repertory();
                    setRepeDatas(repertory);
                    repertory.updateAll("repe_classify_name=? and repe_id=?", repe_classify, repe_id);
                    finish();
                }
                break;
            //view
            case 2:
                break;
            //add
            case 3:
                if (TextUtils.isEmpty(getSpData(classifyList, mSpClassify))
                        || TextUtils.isEmpty(mEtId.getText().toString().trim())) {
                    Toast.makeText(mContext, "信息未完善", Toast.LENGTH_SHORT).show();
                } else if (DataSupport.where("repe_id=?", mEtId.getText().toString().trim())
                        .find(Repertory.class).size() != 0) {
                    Toast.makeText(mContext, "该库存编号已存在", Toast.LENGTH_SHORT).show();
                } else {
                    Repertory repertory = new Repertory();
                    setRepeDatas(repertory);
                    repertory.save();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void setRepeDatas(Repertory repertory) {
        repertory.setRepe_classify_name(getSpData(classifyList, mSpClassify));
        repertory.setRepe_id(mEtId.getText().toString().trim());
        repertory.setRent_state(rent_state[mSpState.getSelectedItemPosition()]);
        repertory.setCpu(getSpData(cpuList, mSpCpu));
        repertory.setMainboard(getSpData(mainboardList, mSpMainboard));
        repertory.setMemory(getSpData(memoryList, mSpMemory));
        repertory.setHardDisk(getSpData(hardDiskList, mSpHardDisk));
        repertory.setGpu(getSpData(gpuList, mSpGpu));
        repertory.setScreen(getSpData(screenList, mSpScreen));
        repertory.setCdDriver(getSpData(cdDriverList, mSpCdDriver));
        repertory.setSoftDriver(getSpData(softDriverList, mSpFloppyDrive));
        repertory.setSoundCard(getSpData(soundCardList, mSpSoundCard));
        repertory.setKeyboard(getSpData(keyBoardList, mSpKeyboard));
        repertory.setMouse(getSpData(mouseList, mSpMouse));
        repertory.setBox(getSpData(boxList, mSpBox));
        repertory.setSoundBox(getSpData(soundBoxList, mSpVoiceBox));
        repertory.setNetworkCard(getSpData(networkList, mSpNetworkCard));
        if (!TextUtils.isEmpty(mEtRent.getText().toString().trim())) {
            repertory.setRent(Float.parseFloat(mEtRent.getText().toString().trim()));
        } else {
            repertory.setRent(0);
        }
        if (!TextUtils.isEmpty(mEtDeposit.getText().toString().trim())) {
            repertory.setDeposit(Float.parseFloat(mEtDeposit.getText().toString().trim()));
        } else {
            repertory.setDeposit(0);
        }
        repertory.setRemark(mEtRemark.getText().toString().trim());
    }

    private String getSpData(List<String> list, Spinner spinner) {
        return list.get(spinner.getSelectedItemPosition());
    }

    private void addRepe() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (dataType == ConstantValue.ADD_DATA) {
                    mSpState.setAdapter(new ArrayAdapter<>(mContext,
                            android.R.layout.simple_spinner_item, rent_state));
                }

                getDataFromDB("repeclassify", classifyList, new Runnable() {
                    @Override
                    public void run() {
                        mSpClassify.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, classifyList));
                    }
                });
                getDataFromDB("cpu", cpuList, new Runnable() {
                    @Override
                    public void run() {
                        mSpCpu.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, cpuList));
                    }
                });
                getDataFromDB("mainboard", mainboardList, new Runnable() {
                    @Override
                    public void run() {
                        mSpMainboard.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, mainboardList));
                    }
                });
                getDataFromDB("memory", memoryList, new Runnable() {
                    @Override
                    public void run() {
                        mSpMemory.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, memoryList));
                    }
                });
                getDataFromDB("harddisk", hardDiskList, new Runnable() {
                    @Override
                    public void run() {
                        mSpHardDisk.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, hardDiskList));
                    }
                });
                getDataFromDB("gpu", gpuList, new Runnable() {
                    @Override
                    public void run() {
                        mSpGpu.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, gpuList));
                    }
                });
                getDataFromDB("screen", screenList, new Runnable() {
                    @Override
                    public void run() {
                        mSpScreen.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, screenList));
                    }
                });
                getDataFromDB("cddriver", cdDriverList, new Runnable() {
                    @Override
                    public void run() {
                        mSpCdDriver.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, cdDriverList));
                    }
                });
                getDataFromDB("softdriver", softDriverList, new Runnable() {
                    @Override
                    public void run() {
                        mSpFloppyDrive.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, softDriverList));
                    }
                });
                getDataFromDB("soundcard", soundCardList, new Runnable() {
                    @Override
                    public void run() {
                        mSpSoundCard.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, soundCardList));
                    }
                });
                getDataFromDB("keyboard", keyBoardList, new Runnable() {
                    @Override
                    public void run() {
                        mSpKeyboard.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, keyBoardList));
                    }
                });
                getDataFromDB("mouse", mouseList, new Runnable() {
                    @Override
                    public void run() {
                        mSpMouse.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, mouseList));
                    }
                });
                getDataFromDB("box", boxList, new Runnable() {
                    @Override
                    public void run() {
                        mSpBox.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, boxList));
                    }
                });
                getDataFromDB("soundbox", soundBoxList, new Runnable() {
                    @Override
                    public void run() {
                        mSpVoiceBox.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, soundBoxList));
                    }
                });
                getDataFromDB("networkcard", networkList, new Runnable() {
                    @Override
                    public void run() {
                        mSpNetworkCard.setAdapter(new ArrayAdapter<>(mContext,
                                android.R.layout.simple_spinner_item, networkList));
                    }
                });
            }
        }).start();
    }

    private List<String> initList(List<String> list) {
        list = new ArrayList<>();
        if (dataType == ConstantValue.ADD_DATA) {
            list.add("");
        }
        return list;
    }

    private List<String> initList(List<String> list, String topItem) {
        list = new ArrayList<>();
        if (dataType == ConstantValue.CHANGE_DATA) {
            list.add(topItem);
        }
        return list;
    }

    private void getDataFromDB(final String tab, final List<String> datas, final Runnable runnable) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase()
                            .rawQuery("select * from " + tab + " order by id", null);
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            datas.add(name);
                        } while (cursor.moveToNext());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            runnable.run();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).start();
    }
}
