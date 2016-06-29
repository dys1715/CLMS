package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 键盘
 */
public class Keyboard extends DataSupport {
    private long id;
    private String keyboard;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getkeyboard() {
        return keyboard;
    }

    public void setkeyboard(String keyboard) {
        this.keyboard = keyboard;
    }
}
