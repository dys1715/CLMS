package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 音箱
 */
public class SoundBox extends DataSupport {
    private long id;
    private String soundBox;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getsoundBox() {
        return soundBox;
    }

    public void setsoundBox(String soundBox) {
        this.soundBox = soundBox;
    }
}
