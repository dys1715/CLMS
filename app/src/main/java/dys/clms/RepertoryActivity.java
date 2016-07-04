package dys.clms;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import dys.clms.bean.db.Repertory;
import dys.clms.common.ConstantValue;

/**
 * Created by Administrator on 2016/6/14 0014.
 * 库存管理
 */
public class RepertoryActivity extends BaseActivity {

    //    private TextView title, back;
    private ListView lvRepe;
    private MyAdapter adapter;
    private List<Repertory> repertoryList = new ArrayList<>();
    private int isFromRentSearch;
    private TextView tvCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repertory);

        tvCount = (TextView) findViewById(R.id.tv_count);
        lvRepe = (ListView) findViewById(R.id.lv_repertory_list);
        adapter = new MyAdapter(repertoryList);
        lvRepe.setAdapter(adapter);
//        getDataFromDB("repertory");
        isFromRentSearch = getIntent().getIntExtra("repe_search", -1);
        lvRepe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isFromRentSearch == 1) {
                    setResult(RESULT_OK, new Intent()
                            .putExtra("repe_id", repertoryList.get(position).getRepe_id())
                            .putExtra("rent", String.valueOf(repertoryList.get(position).getRent()))
                            .putExtra("deposit", String.valueOf(repertoryList.get(position).getDeposit())));
                    finish();
                } else {
                    startActivity(new Intent(mContext, RepertoryDetailsActivity.class)
                            .putExtra("dataType", ConstantValue.VIEW_DATA)
                            .putExtra("repe_id", repertoryList.get(position).getRepe_id())
                            .putExtra("repe_classify", repertoryList.get(position).getRepe_classify_name()));
                }
            }
        });
        lvRepe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(mContext).setItems(new String[]{"修改", "删除"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (repertoryList.get(position).getRent_state().equals("已出租")) {
                                    Toast.makeText(mContext, "当前库存订单已出租，不可修改", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (which == 0) {
                                        startActivity(new Intent(mContext, RepertoryDetailsActivity.class)
                                                .putExtra("dataType", ConstantValue.CHANGE_DATA)
                                                .putExtra("repe_id", repertoryList.get(position).getRepe_id())
                                                .putExtra("repe_classify", repertoryList.get(position).getRepe_classify_name()));
                                    } else {
                                        DataSupport.deleteAll(Repertory.class, "repe_id=? and repe_classify_name=?",
                                                repertoryList.get(position).getRepe_id(),
                                                repertoryList.get(position).getRepe_classify_name());
                                        repertoryList.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataFromDB("repertory");
            }
        }).start();
    }

    @Override
    protected void initTitle() {
        setTitle("库存管理");
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //添加库存
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
        searchView.setQueryHint("请输入分类搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, "submit", Toast.LENGTH_SHORT).show();
                ((MyAdapter) lvRepe.getAdapter()).setFilter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(MainActivity.this, "change", Toast.LENGTH_SHORT).show();
                if (newText.isEmpty())
                    ((MyAdapter) lvRepe.getAdapter()).flushFilter();
                return true;
            }
        });
        return true;
    }

    private void getDataFromDB(final String tab) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repertoryList.clear();
                Cursor cursor = null;
                try {
                    if (isFromRentSearch == 1) {
                        cursor = Connector.getDatabase()
                                .rawQuery("select * from " + tab + " where rent_state='未出租'" + " order by id", null);
                    } else {
                        cursor = Connector.getDatabase()
                                .rawQuery("select * from " + tab + " order by id", null);
                    }
                    if (cursor.moveToFirst()) {
                        do {
                            String classify_name = cursor.getString(cursor.getColumnIndex("repe_classify_name"));
                            String rent_state = cursor.getString(cursor.getColumnIndex("rent_state"));
                            String repe_id = cursor.getString(cursor.getColumnIndex("repe_id"));
                            float rent = cursor.getFloat(cursor.getColumnIndex("rent"));
                            float deposit = cursor.getFloat(cursor.getColumnIndex("deposit"));
                            String cpu = cursor.getString(cursor.getColumnIndex("cpu"));
                            String memory = cursor.getString(cursor.getColumnIndex("memory"));
                            String hardDisk = cursor.getString(cursor.getColumnIndex("harddisk"));
                            String gpu = cursor.getString(cursor.getColumnIndex("gpu"));
                            String screen = cursor.getString(cursor.getColumnIndex("screen"));
                            String cdDriver = cursor.getString(cursor.getColumnIndex("cddriver"));
                            String softDriver = cursor.getString(cursor.getColumnIndex("softdriver"));
                            String soundCard = cursor.getString(cursor.getColumnIndex("soundcard"));
                            String keyboard = cursor.getString(cursor.getColumnIndex("keyboard"));
                            String mouse = cursor.getString(cursor.getColumnIndex("mouse"));
                            String box = cursor.getString(cursor.getColumnIndex("box"));
                            String soundBox = cursor.getString(cursor.getColumnIndex("soundbox"));
                            String networkCard = cursor.getString(cursor.getColumnIndex("networkcard"));
                            String mainboard = cursor.getString(cursor.getColumnIndex("mainboard"));
                            String remark = cursor.getString(cursor.getColumnIndex("remark"));
                            repertoryList.add(new Repertory(classify_name, repe_id, rent_state, rent,
                                    deposit, cpu, memory, hardDisk, gpu, screen, cdDriver, softDriver,
                                    soundCard, keyboard, mouse, box, soundBox, networkCard, mainboard, remark));
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
                            tvCount.setText("库存总数：" + repertoryList.size() + "\t(长按可编辑)");
                        }
                    });
                }
            }
        }).start();

    }

    class MyAdapter extends BaseAdapter {

        private List<Repertory> mList;
        private List<Repertory> searchList;
        private List<Repertory> oldList;

        public MyAdapter(List<Repertory> mList) {
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
                holder.id = (TextView) convertView.findViewById(R.id.tv_repe_id);
                holder.rent = (TextView) convertView.findViewById(R.id.tv_rent);
                holder.deposit = (TextView) convertView.findViewById(R.id.tv_deposit);
                holder.cpu = (TextView) convertView.findViewById(R.id.tv_cpu);
                holder.gpu = (TextView) convertView.findViewById(R.id.tv_gpu);
                holder.mainboard = (TextView) convertView.findViewById(R.id.tv_mainboard);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.classify.setText("分类：" + mList.get(position).getRepe_classify_name());
            holder.state.setText("库存状态：" + mList.get(position).getRent_state());
            holder.id.setText("库存编号：" + mList.get(position).getRepe_id());
            holder.rent.setText("月租金：" + mList.get(position).getRent());
            holder.deposit.setText("押金：" + mList.get(position).getDeposit());
            holder.cpu.setText("CPU：" + mList.get(position).getCpu());
            holder.mainboard.setText("主板：" + mList.get(position).getMainboard());
            holder.gpu.setText("GPU：" + mList.get(position).getGpu());
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
                if (mList.get(i).getRepe_classify_name().toLowerCase().contains(queryText)) {
                    searchList.add(mList.get(i));
                }
            }
            mList = new ArrayList<>();
            mList.addAll(searchList);
            notifyDataSetChanged();
        }

        class ViewHolder {
            TextView classify, state, rent, deposit, cpu, gpu, mainboard, id;
        }
    }
}
