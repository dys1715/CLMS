package dys.clms.bean.db.config;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 网卡
 */
public class NetworkCard extends DataSupport {
    private long id;
    private String networkCard;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getnetworkCard() {
        return networkCard;
    }

    public void setnetworkCard(String networkCard) {
        this.networkCard = networkCard;
    }
}
