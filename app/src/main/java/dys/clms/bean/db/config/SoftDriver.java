package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 软驱
 */
public class SoftDriver extends DataSupport {
    private long id;
    private String softDriver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getsoftDriver() {
        return softDriver;
    }

    public void setsoftDriver(String softDriver) {
        this.softDriver = softDriver;
    }
}
