package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 客户分类表
 */
public class CustomerClassify extends DataSupport {
    private String name;   //客户分类名称，如“一般客户”
    private int rate;      //折扣率，如100

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
