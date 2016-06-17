package dys.clms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dys.clms.common.ConstantValue;

/**
 * Created by Administrator on 2016/6/14 0014.
 * 库存管理
 */
public class RepertoryActivity extends BaseActivity {

    //    private TextView title, back;
    private ListView repertoryList;
    private List<String> mList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertory);
        //初始化假数据
        mList = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            mList.add(i + "");
        }
        repertoryList = (ListView) findViewById(R.id.lv_repertory_list);
        adapter = new MyAdapter(mList);
        repertoryList.setAdapter(adapter);
        repertoryList.setTextFilterEnabled(true);
        repertoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext, RepertoryDetailsActivity.class)
                        .putExtra("dataType", ConstantValue.VIEW_DATA));
            }
        });
        repertoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(mContext).setItems(new String[]{"修改", "删除"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0){
                                    startActivity(new Intent(mContext,RepertoryDetailsActivity.class)
                                            .putExtra("dataType",ConstantValue.CHANGE_DATA));
                                }else {
                                    mList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    protected void initTitle() {
        setTitle("库存管理");
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_add:
//                        Toast.makeText(RepertoryActivity.this, "add", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, RepertoryDetailsActivity.class)
                                .putExtra("dataType", ConstantValue.ADD_DATA));
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repertory_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, "submit", Toast.LENGTH_SHORT).show();
                ((MyAdapter) repertoryList.getAdapter()).setFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(MainActivity.this, "change", Toast.LENGTH_SHORT).show();
                if (newText.isEmpty())
                    ((MyAdapter) repertoryList.getAdapter()).flushFilter();
                return true;
            }
        });
        return true;
    }

    class MyAdapter extends BaseAdapter {

        private List<String> mList;
        private List<String> searchList;
        private List<String> oldList;

        public MyAdapter(List<String> mList) {
            this.mList = mList;
            oldList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(RepertoryActivity.this).inflate(R.layout.item_repe_list, null);
                holder = new ViewHolder();
                holder.classify = (TextView) convertView.findViewById(R.id.tv_classify);
                holder.state = (TextView) convertView.findViewById(R.id.tv_classify_state);
                holder.rent = (TextView) convertView.findViewById(R.id.tv_rent);
                holder.deposit = (TextView) convertView.findViewById(R.id.tv_deposit);
                holder.cpu = (TextView) convertView.findViewById(R.id.tv_cpu);
                holder.gpu = (TextView) convertView.findViewById(R.id.tv_gpu);
                holder.mainboard = (TextView) convertView.findViewById(R.id.tv_mainboard);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.classify.setText("分类" + mList.get(position));
            return convertView;
        }

        public void flushFilter() {
            searchList = new ArrayList<>();
            mList = new ArrayList<>();
            mList.addAll(oldList);
            notifyDataSetChanged();
        }

        public void setFilter(String queryText) {
            searchList = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).toLowerCase().contains(queryText)) {
                    searchList.add(mList.get(i));
                }
            }
            mList = new ArrayList<>();
            mList.addAll(searchList);
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView classify,state,rent,deposit,cpu,gpu,mainboard;
        }
    }
}
