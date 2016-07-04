package dys.clms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dys.clms.bean.db.Pact;
import dys.clms.common.CommonMethod;

/**
 * 到期订单
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView remindList;
    private TextView count;
    private MyAdapter mAdapter;
    private List<Pact> mPactList = new ArrayList<>();
    private int useCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        useCount = CommonMethod.setSharedPreference(MainActivity.this);
        Log.i("count>>>>>>>>>>>", useCount + "");
        if (useCount > 200) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("到期提示")
                    .setMessage("200次试用体验已结束，如继续使用，请联系作者[QQ：289133385]")
                    .setCancelable(false)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPactList.clear();
        List<Pact> temp = DataSupport.findAll(Pact.class);
//        mPactList.addAll(DataSupport.findAll(Pact.class));
        for (int i = 0; i < temp.size(); i++) {
            if (dateDiff(temp.get(i).getEnd_time()) > 0) {
                if (!temp.get(i).getPact_state().equals("已终止")) {
                    mPactList.add(temp.get(i));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        count.setText("到期合同总数：" + remindList.getAdapter().getItemCount()
                + "\t**当前剩余可使用次数" + (200 - useCount) + "**");
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("到期合同");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        remindList = (RecyclerView) findViewById(R.id.rv_remind_list);
        remindList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mPactList);
        remindList.setAdapter(mAdapter);

        count = (TextView) findViewById(R.id.tv_count);
    }

    private long dateDiff(String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 0;
        try {
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间： Date curDate = new Date(System.currentTimeMillis());
            Date d2 = df.parse(endTime);
            diff = curDate.getTime() - d2.getTime();//这样得到的差值是微秒级别

            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

            Log.i("MainActivity timeDiff", "" + days + "天" + hours + "小时" + minutes + "分");

        } catch (Exception e) {
        }
        return diff;
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
                    from(parent.getContext()).inflate(R.layout.item_main_list, parent, false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.id.setText("库存编号：" + list.get(position).getRepe_id());
            holder.name.setText("姓名：" + list.get(position).getCustomer_name());
            holder.tel.setText("电话：" + list.get(position).getCustomer_tel());
            holder.rentTime.setText("出租时间：" + list.get(position).getBegin_time());
            holder.overTime.setText("截止时间：" + list.get(position).getEnd_time());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PactDetailsActivity.class)
                            .putExtra("repe_id", list.get(position).getRepe_id())
                            .putExtra("customer_name", list.get(position).getCustomer_name()));
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
            TextView id, name, tel, rentTime, overTime;

            public MyViewHolder(View itemView) {
                super(itemView);
                root = (LinearLayout) itemView.findViewById(R.id.ll_root);
                id = (TextView) itemView.findViewById(R.id.tv_id);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                tel = (TextView) itemView.findViewById(R.id.tv_tel);
                rentTime = (TextView) itemView.findViewById(R.id.tv_rent_time);
                overTime = (TextView) itemView.findViewById(R.id.tv_over_time);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    SearchView searchView;
    MenuItem menuItemSearch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItemSearch = menu.findItem(R.id.ab_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(menuItemSearch);
        assert searchView != null;
        searchView.setQueryHint("请输入姓名查询");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            //库存管理
            case R.id.nav_repertory_manage:
                startActivity(new Intent(this, RepertoryActivity.class));
                break;
            //出租电脑
            case R.id.nav_rent_computer:
                startActivity(new Intent(this, RentActivity.class));
                break;
            //合同查询
            case R.id.nav_pact_search:
                startActivity(new Intent(this, PactSearchActivity.class));
                break;
//            //收支统计
//            case R.id.nav_income_expand_info:
//                break;
//            //押金统计
//            case R.id.nav_deposit_info:
//                break;
            //设置
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            //关于
            case R.id.nav_about:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            default:
                break;
        }

//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
