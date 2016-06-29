package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 内存
 */
public class Memory extends DataSupport {
    private long id;
    private String memory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmemory() {
        return memory;
    }

    public void setmemory(String memory) {
        this.memory = memory;
    }
}
