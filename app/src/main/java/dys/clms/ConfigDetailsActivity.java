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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dys on 2016/6/28 0028.
 * 编辑配置
 */
public class ConfigDetailsActivity extends BaseActivity {

    private ArrayList<String> detailsData;
    private DetailsAdapter mAdapter;
    @BindView(R.id.lv_config_details)
    ListView mLvConfigDetails;
    @BindView(R.id.btn_add)
    Button mBtnAdd;

    @OnClick(R.id.btn_add)
    public void onClick() {
        final EditText editText = new EditText(mContext);
        new AlertDialog.Builder(mContext)
                .setTitle("新增数据")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
