package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 客户分类表
 */
public class CustomerClassify extends DataSupport {
    private long id;
    private int customer_classify;  //客户分类标记，如0：一般客户
    private String customer_name;   //客户分类名称，如“一般客户”
    private int rate;               //折扣率，如100

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCustomer_classify() {
        return customer_classify;
    }

    public void setCustomer_classify(int customer_classify) {
        this.customer_classify = customer_classify;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
