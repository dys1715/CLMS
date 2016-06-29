package dys.clms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dys.clms.bean.Config;
import dys.clms.view.NoScrollGridView;

/**
 * Created by Administrator on 2016/6/26 0026.
 * 常用配置维护
 */
public class ConfigurationsActivity extends BaseActivity {

    private RecyclerView configList;
    private MyAdapter adapter;
    private ArrayList<Config> configs = new ArrayList<>();
    private ArrayList<String> configsItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations_activity);
        initData();
        configList = (RecyclerView) findViewById(R.id.rv_config);
        configList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter();
        configList.setAdapter(adapter);
    }

    private void initData() {
        //插入假数据
        configsItem = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            configsItem.add(j + "");
        }
        configs.add(new Config("CPU", configsItem));
        configs.add(new Config("内存", configsItem));
        configs.add(new Config("硬盘", configsItem));
        configs.add(new Config("主板", configsItem));
        configs.add(new Config("显卡", configsItem));
        configs.add(new Config("显示器", configsItem));
        configs.add(new Config("机箱", configsItem));
        configs.add(new Config("键盘", configsItem));
        configs.add(new Config("鼠标", configsItem));
        configs.add(new Config("光驱", configsItem));
        configs.add(new Config("软驱", configsItem));
        configs.add(new Config("声卡", configsItem));
        configs.add(new Config("网卡", configsItem));

    }

    @Override
    protected void initTitle() {
        setTitle("常用配置维护");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.e("aaaaaaaaaaaaa", "------------------------------");
            ArrayList<String> gridList = data.getStringArrayListExtra("listData");
            int position = data.getIntExtra("position", -1);
            configs.remove(position);
            configs.add(position, new Config(position + "", gridList));
            adapter.notifyDataSetChanged();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        //        private ArrayList<String> gridList;
        private GridAdapter adapter;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_config, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.title.setText(configs.get(position).getConfigTitle());
//            gridList = configs.get(position).getConfig();
            adapter = new GridAdapter(configs.get(position).getConfig());
            holder.gridView.setAdapter(adapter);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(mContext, ConfigDetailsActivity.class)
                            .putExtra("position", position)
                            .putStringArrayListExtra("gridList", configs.get(position).getConfig()), 1000);
                }
            });
        }

        @Override
        public int getItemCount() {
            return configs.size();
        }

        class GridAdapter extends BaseAdapter {
            private List<String> gridList;

            public GridAdapter(List<String> gridList) {
                this.gridList = gridList;
            }

            @Override
            public int getCount() {
                return gridList.size();
            }

            @Override
            public Object getItem(int position) {
                return gridList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_gridview, null);
                    holder = new ViewHolder();
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.textView.setText(gridList.get(position));
                return convertView;
            }

            class ViewHolder {
                TextView textView;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title, edit;
            NoScrollGridView gridView;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.tv_config_title);
                edit = (TextView) itemView.findViewById(R.id.tv_edit);
                gridView = (NoScrollGridView) itemView.findViewById(R.id.gridview);
            }
        }
    }
}
