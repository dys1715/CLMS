package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 鼠标
 */
public class Mouse extends DataSupport {
    private long id;
    private String mouse;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmouse() {
        return mouse;
    }

    public void setmouse(String mouse) {
        this.mouse = mouse;
    }
}
