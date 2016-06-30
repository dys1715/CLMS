package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 */
public class Repertory extends DataSupport {
    private long id;
    private int repe_classify;
    private int rent_state;     //0：未出租，1：已出租
    private float rent;         //租金
    private float deposit;      //押金
    private Configurations configs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRepe_classify() {
        return repe_classify;
    }

    public void setRepe_classify(int repe_classify) {
        this.repe_classify = repe_classify;
    }

    public int getRent_state() {
        return rent_state;
    }

    public void setRent_state(int rent_state) {
        this.rent_state = rent_state;
    }

    public float getRent() {
        return rent;
    }

    public void setRent(float rent) {
        this.rent = rent;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public Configurations getConfigs() {
        return configs;
    }

    public void setConfigs(Configurations configs) {
        this.configs = configs;
    }
}
