package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 */
public class GPU extends DataSupport {
    private long id;
    private String gpu;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getgpu() {
        return gpu;
    }

    public void setgpu(String gpu) {
        this.gpu = gpu;
    }
}
