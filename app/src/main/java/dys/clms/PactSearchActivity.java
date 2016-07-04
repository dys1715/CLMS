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
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import dys.clms.bean.db.CompanyInfo;
import dys.clms.bean.db.Pact;
import dys.clms.bean.db.Repertory;

/**
 * Created by Administrator on 2016/6/22 0022.
 * 合同查询
 */
public class PactSearchActivity extends BaseActivity {

    private RecyclerView remindList;
    private TextView count;
    private List<Pact> mPactList = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pact_search);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPactList.clear();
        mPactList.addAll(DataSupport.findAll(Pact.class));
        adapter.notifyDataSetChanged();
        count.setText("合同总数：" + remindList.getAdapter().getItemCount()+"\t(长按可编辑)");
    }

    @Override
    protected void initTitle() {
        setTitle("合同查询");
    }

    private void initView() {
        adapter = new MyAdapter(mPactList);
        remindList = (RecyclerView) findViewById(R.id.rv_remind_list);
        remindList.setLayoutManager(new LinearLayoutManager(this));
        remindList.setAdapter(adapter);

        count = (TextView) findViewById(R.id.tv_count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.ab_search);//在菜单中找到对应控件的item
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItemSearch);
        assert searchView != null;
        searchView.setQueryHint("请输入姓名搜索");
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
        private List<Pact> list;
        private List<Pact> searchList;
        private List<Pact> oldList;

        public MyAdapter(List<Pact> mPactList) {
            list = mPactList;
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
            holder.id.setText("库存编号：" + list.get(position).getRepe_id());
            holder.state.setText("合同状态：" + list.get(position).getPact_state());
            holder.name.setText("姓名：" + list.get(position).getCustomer_name());
            holder.tel.setText("电话：" + list.get(position).getCustomer_tel());
            holder.rentTime.setText("出租时间：" + list.get(position).getBegin_time());
            holder.overTime.setText("截止时间：" + list.get(position).getEnd_time());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, PactDetailsActivity.class)
                    .putExtra("repe_id",list.get(position).getRepe_id())
                    .putExtra("customer_name",list.get(position).getCustomer_name())
                    .putExtra("pact_state",list.get(position).getPact_state()));
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
                                    if (list.get(position).getPact_state().equals("已终止")){
                                        DataSupport.deleteAll(Pact.class,"repe_id=? and customer_name=?",
                                                list.get(position).getRepe_id(),
                                                list.get(position).getCustomer_name());
                                        Repertory repertory = new Repertory();
                                        repertory.setRent_state("未出租");
                                        repertory.updateAll("repe_id=?",list.get(position).getRepe_id());
                                        mPactList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }else {
                                        Toast.makeText(mContext,"当前合同未中止，不可删除",Toast.LENGTH_SHORT).show();
                                    }
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
                if (list.get(i).getCustomer_name().toLowerCase().contains(queryText)) {
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
