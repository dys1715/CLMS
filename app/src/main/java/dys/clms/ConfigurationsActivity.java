package dys.clms;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import dys.clms.bean.Config;
import dys.clms.view.NoScrollGridView;

/**
 * Created by Administrator on 2016/6/26 0026.
 * 常用配置维护
 */
public class ConfigurationsActivity extends BaseActivity {

    private RecyclerView rvConfig;
    private MyAdapter adapter;
    private ArrayList<Config> configs = new ArrayList<>();
    private ArrayList<String> cpuList, memoryList, hardDiskList, mainboardList, gpuList, screenList, boxList;
    private ArrayList<String> keyboardList, mouseList, cdDriverList, softDriverList, soundCardList, soundBoxList, networkCardList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configurations_activity);
        initData();
        rvConfig = (RecyclerView) findViewById(R.id.rv_config);
        rvConfig.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MyAdapter();
        rvConfig.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getConfigDatasFromDB("cpu",cpuList);
                getConfigDatasFromDB("memory",memoryList);
                getConfigDatasFromDB("harddisk",hardDiskList);
                getConfigDatasFromDB("mainboard",mainboardList);
                getConfigDatasFromDB("gpu",gpuList);
                getConfigDatasFromDB("screen",screenList);
                getConfigDatasFromDB("box",boxList);
                getConfigDatasFromDB("keyboard",keyboardList);
                getConfigDatasFromDB("mouse",mouseList);
                getConfigDatasFromDB("cddriver",cdDriverList);
                getConfigDatasFromDB("softdriver",softDriverList);
                getConfigDatasFromDB("soundcard",soundCardList);
                getConfigDatasFromDB("soundbox",soundBoxList);
                getConfigDatasFromDB("networkcard",networkCardList);
            }
        }).start();

    }

    private void initData() {
        cpuList = new ArrayList<>();
        memoryList = new ArrayList<>();
        hardDiskList = new ArrayList<>();
        mainboardList = new ArrayList<>();
        gpuList = new ArrayList<>();
        screenList = new ArrayList<>();
        boxList = new ArrayList<>();
        keyboardList = new ArrayList<>();
        mouseList = new ArrayList<>();
        cdDriverList = new ArrayList<>();
        softDriverList = new ArrayList<>();
        soundCardList = new ArrayList<>();
        soundBoxList = new ArrayList<>();
        networkCardList = new ArrayList<>();

        configs.add(new Config("CPU", cpuList));
        configs.add(new Config("内存", memoryList));
        configs.add(new Config("硬盘", hardDiskList));
        configs.add(new Config("主板", mainboardList));
        configs.add(new Config("显卡", gpuList));
        configs.add(new Config("显示器", screenList));
        configs.add(new Config("机箱", boxList));
        configs.add(new Config("键盘", keyboardList));
        configs.add(new Config("鼠标", mouseList));
        configs.add(new Config("音箱", soundBoxList));
        configs.add(new Config("光驱", cdDriverList));
        configs.add(new Config("软驱", softDriverList));
        configs.add(new Config("声卡", soundCardList));
        configs.add(new Config("网卡", networkCardList));
    }

    @Override
    protected void initTitle() {
        setTitle("常用配置维护");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<String> gridList = data.getStringArrayListExtra("listData");
            int position = data.getIntExtra("position", -1);
            String title = configs.get(position).getConfigTitle();
            configs.remove(position);
            configs.add(position, new Config(title + "", gridList));
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取所有配置信息
     * @param tab
     * @param list
     */
    private void getConfigDatasFromDB(final String tab, final ArrayList<String> list) {
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
                            list.add(name);
                        } while (cursor.moveToNext());
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
//                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
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
            //编辑按钮
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(mContext, ConfigDetailsActivity.class)
                            .putExtra("position", position)
                            .putExtra("title", configs.get(position).getConfigTitle())
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
