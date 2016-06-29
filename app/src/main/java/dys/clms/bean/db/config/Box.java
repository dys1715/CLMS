package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 机箱
 */
public class Box extends DataSupport {
    private long id;
    private String box;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getbox() {
        return box;
    }

    public void setbox(String box) {
        this.box = box;
    }
}
