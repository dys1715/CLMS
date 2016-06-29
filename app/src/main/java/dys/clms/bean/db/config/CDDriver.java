package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 光驱
 */
public class CDDriver extends DataSupport {
    private long id;
    private String cdDriver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getcdDriver() {
        return cdDriver;
    }

    public void setcdDriver(String cdDriver) {
        this.cdDriver = cdDriver;
    }
}
