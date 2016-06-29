package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 */
public class SoundCard extends DataSupport {
    private long id;
    private String soundCard;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getsoundCard() {
        return soundCard;
    }

    public void setsoundCard(String soundCard) {
        this.soundCard = soundCard;
    }
}
