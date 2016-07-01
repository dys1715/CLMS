package dys.clms;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dys.clms.bean.Customer;
import dys.clms.bean.db.CustomerClassify;

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
        adapter = new MyClassifyAdapter();
        mLvConfigDetails.setAdapter(adapter);
        getDataFromDB("customerclassify");
    }

    @Override
    protected void initTitle() {
        setTitle("用户分类设置");
    }

    @OnClick(R.id.btn_add)
    public void onClick() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_customer_classify, null);
        final EditText etClassify = (EditText) view.findViewById(R.id.et_customer_classify);
        final EditText etRate = (EditText) view.findViewById(R.id.et_customer_rate);
        new AlertDialog.Builder(mContext)
                .setView(view)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(etClassify.getText().toString())
                                || TextUtils.isEmpty(etRate.getText().toString())) {
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
                            CustomerClassify customerClassify = new CustomerClassify();
                            customerClassify.setName(etClassify.getText().toString());
                            customerClassify.setRate(Integer.parseInt(etRate.getText().toString()));
                            customerClassify.save();
                            customerList.add(new Customer(etClassify.getText().toString(), Integer.parseInt(etRate.getText().toString())));
                            adapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }
                })
                .show();
    }

    private void getDataFromDB(final String tab) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                try {
                    cursor = Connector.getDatabase().rawQuery("select * from " + tab + " order by id", null);
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            int rate = cursor.getInt(cursor.getColumnIndex("rate"));
                            customerList.add(new Customer(name, rate));
                        } while (cursor.moveToNext());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "没有数据", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
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

        @SuppressLint("SetTextI18n")
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
            holder.tvRate.setText("打折率：" + customerList.get(position).getRate());
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataSupport.deleteAll(CustomerClassify.class, "name=?", customerList.get(position).getClassify());
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
