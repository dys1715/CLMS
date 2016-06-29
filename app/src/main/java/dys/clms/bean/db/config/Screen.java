package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 */
public class Screen extends DataSupport {
    private long id;
    private String screen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getscreen() {
        return screen;
    }

    public void setscreen(String screen) {
        this.screen = screen;
    }
}
