package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 硬盘
 */
public class HardDisk extends DataSupport {
    private long id;
    private String hardDisk;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String gethardDisk() {
        return hardDisk;
    }

    public void sethardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }
}
