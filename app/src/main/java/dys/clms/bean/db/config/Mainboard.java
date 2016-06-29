package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 主板
 */
public class Mainboard extends DataSupport {
    private long id;
    private String mainboard;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmainboard() {
        return mainboard;
    }

    public void setmainboard(String mainboard) {
        this.mainboard = mainboard;
    }
}
