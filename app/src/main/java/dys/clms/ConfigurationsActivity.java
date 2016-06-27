package dys.clms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 */
public class ConfigurationsActivity extends BaseActivity {

    private RecyclerView configList;
    private MyAdapter adapter;
    private List<Config> configs;
    private List<String> configsItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations_activity);
        configs = new ArrayList<>();
        //插入假数据
        for (int i = 0; i < 10; i++) {
            configsItem = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                configsItem.add(j + "");
            }
            configs.add(new Config(i + "", configsItem));
        }

        configList = (RecyclerView) findViewById(R.id.rv_config);
        configList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter(configs);
        configList.setAdapter(adapter);
    }

    @Override
    protected void initTitle() {
        setTitle("常用配置维护");
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<Config> mList;
        private List<String> gridList;
        private GridAdapter adapter;

        public MyAdapter(List<Config> mList) {
            this.mList = mList;
            gridList = new ArrayList<>();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_config, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.title.setText(mList.get(position).getConfigTitle());
            gridList = mList.get(position).getConfig();
            adapter = new GridAdapter(gridList);
            holder.gridView.setAdapter(adapter);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
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
