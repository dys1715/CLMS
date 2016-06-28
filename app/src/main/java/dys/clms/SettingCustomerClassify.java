package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.Customer;

/**
 * Created by dys on 2016/6/28 0028.
 * 用户分类设置
 */
public class SettingCustomerClassify extends BaseActivity {

    @BindView(R.id.lv_config_details)
    ListView mLvConfigDetails;
    @BindView(R.id.btn_add)
    Button mBtnAdd;
    //    private ArrayList<String> customerList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private MyClassifyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_details);
        ButterKnife.bind(this);
        //插入假数据
        for (int i = 0; i < 3; i++) {
            customerList.add(new Customer(i+"",(100-i*2)+""));
        }
        adapter = new MyClassifyAdapter();
        mLvConfigDetails.setAdapter(adapter);
    }

    @Override
    protected void initTitle() {
        setTitle("用户分类设置");
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
    }

    class MyClassifyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return customerList.size();
        }

        @Override
        public Object getItem(int position) {
            return customerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_customer_classify, null);
                holder = new ViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvRate = (TextView) convertView.findViewById(R.id.tv_discount_rate);
                holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(customerList.get(position).getClassify());
            holder.tvRate.setText("打折率："+customerList.get(position).getDiscountRate());
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customerList.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView tvName, tvRate;
            ImageView ivDelete;
        }
    }
}
