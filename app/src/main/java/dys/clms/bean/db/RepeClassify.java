package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 库存分类
 */
public class RepeClassify extends DataSupport {
    //    private int classifyId; //分类标志 如0：笔记本 1：电脑
    private String name;  //分类名称 如“笔记本”

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
