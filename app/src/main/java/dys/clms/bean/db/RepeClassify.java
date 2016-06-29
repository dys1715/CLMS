package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 库存分类
 */
public class RepeClassify extends DataSupport {
    private long id;
    private int repe_classify; //分类标志 如0：笔记本 1：电脑
    private String repe_name;  //分类名称 如“笔记本”

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRepe_classify() {
        return repe_classify;
    }

    public void setRepe_classify(int repe_classify) {
        this.repe_classify = repe_classify;
    }

    public String getRepe_name() {
        return repe_name;
    }

    public void setRepe_name(String repe_name) {
        this.repe_name = repe_name;
    }
}
