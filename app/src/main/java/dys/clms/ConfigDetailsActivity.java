package dys.clms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.db.Configurations;
import dys.clms.bean.db.config.Box;
import dys.clms.bean.db.config.CDDriver;
import dys.clms.bean.db.config.CPU;
import dys.clms.bean.db.config.GPU;
import dys.clms.bean.db.config.HardDisk;
import dys.clms.bean.db.config.Keyboard;
import dys.clms.bean.db.config.Mainboard;
import dys.clms.bean.db.config.Memory;
import dys.clms.bean.db.config.Mouse;
import dys.clms.bean.db.config.NetworkCard;
import dys.clms.bean.db.config.Screen;
import dys.clms.bean.db.config.SoftDriver;
import dys.clms.bean.db.config.SoundBox;
import dys.clms.bean.db.config.SoundCard;

/**
 * Created by dys on 2016/6/28 0028.
 * 编辑配置
 */
public class ConfigDetailsActivity extends BaseActivity {

    private ArrayList<String> detailsData;
    private DetailsAdapter mAdapter;
    private String itemTitle;
    @BindView(R.id.lv_config_details)
    ListView mLvConfigDetails;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    //新增数据
    @OnClick(R.id.btn_add)
    public void onClick() {
        final EditText editText = new EditText(mContext);
        new AlertDialog.Builder(mContext)
                .setTitle("新增数据")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveData(editText.getText().toString().trim());
                        detailsData.add(editText.getText().toString().trim());
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_details);
        ButterKnife.bind(this);
        //获取列表
        detailsData = getIntent().getStringArrayListExtra("gridList");
        //获取分类名
        itemTitle = getIntent().getStringExtra("title");
        //设置适配器
        mAdapter = new DetailsAdapter();
        mLvConfigDetails.setAdapter(mAdapter);
    }

    @Override
    protected void initTitle() {
        setTitle("编辑配置");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult();
            }
        });
    }

    private void saveData(String name) {
        switch (itemTitle) {
            case "CPU":
                CPU cpu = new CPU();
                cpu.setName(name);
                cpu.save();
                break;
            case "内存":
                Memory memory = new Memory();
                memory.setName(name);
                memory.save();
                break;
            case "硬盘":
                HardDisk hardDisk = new HardDisk();
                hardDisk.setName(name);
                hardDisk.save();
                break;
            case "主板":
                Mainboard mainboard = new Mainboard();
                mainboard.setName(name);
                mainboard.save();
                break;
            case "显卡":
                GPU gpu = new GPU();
                gpu.setName(name);
                gpu.save();
                break;
            case "显示器":
                Screen screen = new Screen();
                screen.setName(name);
                screen.save();
                break;
            case "机箱":
                Box box = new Box();
                box.setName(name);
                box.save();
                break;
            case "键盘":
                Keyboard keyboard = new Keyboard();
                keyboard.setName(name);
                keyboard.save();
                break;
            case "鼠标":
                Mouse mouse = new Mouse();
                mouse.setName(name);
                mouse.save();
                break;
            case "音箱":
                SoundBox soundBox = new SoundBox();
                soundBox.setName(name);
                soundBox.save();
                break;
            case "光驱":
                CDDriver cdDriver = new CDDriver();
                cdDriver.setName(name);
                cdDriver.save();
                break;
            case "软驱":
                SoftDriver softDriver = new SoftDriver();
                softDriver.setName(name);
                softDriver.save();
                break;
            case "声卡":
                SoundCard soundCard = new SoundCard();
                soundCard.setName(name);
                soundCard.save();
                break;
            case "网卡":
                NetworkCard networkCard = new NetworkCard();
                networkCard.setName(name);
                networkCard.save();
                break;
            default:
                break;
        }

    }

    private void deleteData(String name) {
        switch (itemTitle) {
            case "CPU":
                DataSupport.deleteAll(CPU.class, "name=?", name);
                break;
            case "内存":
                DataSupport.deleteAll(Memory.class, "name=?", name);
                break;
            case "硬盘":
                DataSupport.deleteAll(HardDisk.class, "name=?", name);
                break;
            case "主板":
                DataSupport.deleteAll(Mainboard.class, "name=?", name);
                break;
            case "显卡":
                DataSupport.deleteAll(GPU.class, "name=?", name);
                break;
            case "显示器":
                DataSupport.deleteAll(Screen.class, "name=?", name);
                break;
            case "机箱":
                DataSupport.deleteAll(Box.class, "name=?", name);
                break;
            case "键盘":
                DataSupport.deleteAll(Keyboard.class, "name=?", name);
                break;
            case "鼠标":
                DataSupport.deleteAll(Mouse.class, "name=?", name);
                break;
            case "音箱":
                DataSupport.deleteAll(SoundBox.class, "name=?", name);
                break;
            case "光驱":
                DataSupport.deleteAll(CDDriver.class, "name=?", name);
                break;
            case "软驱":
                DataSupport.deleteAll(SoftDriver.class, "name=?", name);
                break;
            case "声卡":
                DataSupport.deleteAll(SoundCard.class, "name=?", name);
                break;
            case "网卡":
                DataSupport.deleteAll(NetworkCard.class, "name=?", name);
                break;
            default:
                break;
        }
    }

    /**
     * 重写返回键方法，setResult
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            setResult();
        }
        return false;
    }

    private void setResult() {
        setResult(RESULT_OK, new Intent().putStringArrayListExtra("listData", detailsData)
                .putExtra("position", getIntent().getIntExtra("position", -1)));
        finish();
    }

    /**
     * 自定义adapter
     */
    class DetailsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return detailsData.size();
        }

        @Override
        public Object getItem(int position) {
            return detailsData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_config_details, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(detailsData.get(position));
            //删除一条数据
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteData(detailsData.get(position).trim());
                    detailsData.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tvName;
            ImageView ivDelete;
        }
    }
}
