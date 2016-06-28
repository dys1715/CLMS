package dys.clms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dys on 2016/6/28 0028.
 * 库存分类设置
 */
public class SettingRepeClassifyActivity extends BaseActivity {

    @BindView(R.id.lv_config_details)
    ListView mLvConfigDetails;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    private ArrayList<String> repeList = new ArrayList<>();
    private MyClassifyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_details);
        ButterKnife.bind(this);
        //插入假数据
        for (int i = 0; i < 10; i++) {
            repeList.add(i + "");
        }
        adapter = new MyClassifyAdapter();
        mLvConfigDetails.setAdapter(adapter);
    }

    @Override
    protected void initTitle() {
        setTitle("库存分类设置");
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
        final EditText editText = new EditText(mContext);
        new AlertDialog.Builder(mContext)
                .setTitle("新增数据")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repeList.add(editText.getText().toString().trim());
                        adapter.notifyDataSetChanged();
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

    class MyClassifyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return repeList.size();
        }

        @Override
        public Object getItem(int position) {
            return repeList.get(position);
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
            holder.tvName.setText(repeList.get(position));
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    repeList.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder{
            TextView tvName;
            ImageView ivDelete;
        }
    }
}
