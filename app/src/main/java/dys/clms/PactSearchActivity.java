package dys.clms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class PactSearchActivity extends BaseActivity {

    private RecyclerView remindList;
    private TextView count;
    private List<String> mList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_search);
        initView();
    }

    @Override
    protected void initTitle() {
        setTitle("合同查询");
    }

    private void initView() {
        //初始化假数据
        mList = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            mList.add(i + "");
        }
        adapter = new MyAdapter(mList);
        remindList = (RecyclerView) findViewById(R.id.rv_remind_list);
        remindList.setLayoutManager(new LinearLayoutManager(this));
        remindList.setAdapter(adapter);

        count = (TextView) findViewById(R.id.tv_count);
        count.setText("合同总数：" + remindList.getAdapter().getItemCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.ab_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItemSearch);
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, "submit", Toast.LENGTH_SHORT).show();
                ((MyAdapter) remindList.getAdapter()).setFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(MainActivity.this, "change", Toast.LENGTH_SHORT).show();
                if (newText.isEmpty())
                    ((MyAdapter) remindList.getAdapter()).flushFilter();
                return true;
            }
        });
        return true;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<String> list;
        private List<String> searchList;
        private List<String> oldList;

        public MyAdapter(List<String> mList) {
            list = mList;
            oldList = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.
                    from(parent.getContext()).inflate(R.layout.item_pact_list, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.id.setText("合同编号：" + list.get(position));
            holder.state.setText("合同状态：" + list.get(position));
            holder.name.setText("姓名：" + list.get(position));
            holder.tel.setText("电话：" + list.get(position));
            holder.rentTime.setText("出租时间：" + list.get(position) + "");
            holder.overTime.setText("截止时间：" + list.get(position) + "");
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, PactDetailsActivity.class));
                }
            });
            holder.root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("是否删除该条数据?")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mList.remove(position);
                                    adapter.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void flushFilter() {
            searchList = new ArrayList<>();
            list = new ArrayList<>();
            list.addAll(oldList);
            notifyDataSetChanged();
        }

        public void setFilter(String queryText) {
            searchList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).toLowerCase().contains(queryText)) {
                    searchList.add(list.get(i));
                }
            }
            list = new ArrayList<>();
            list.addAll(searchList);
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout root;
            TextView id, state, name, tel, rentTime, overTime;

            public MyViewHolder(View itemView) {
                super(itemView);
                root = (LinearLayout) itemView.findViewById(R.id.ll_root);
                id = (TextView) itemView.findViewById(R.id.tv_id);
                state = (TextView)itemView.findViewById(R.id.tv_state);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                tel = (TextView) itemView.findViewById(R.id.tv_tel);
                rentTime = (TextView) itemView.findViewById(R.id.tv_rent_time);
                overTime = (TextView) itemView.findViewById(R.id.tv_over_time);
            }
        }
    }
}
