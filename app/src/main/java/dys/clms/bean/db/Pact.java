package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by dys on 2016/6/29 0029.
 * 合同表
 */
public class Pact extends DataSupport {
    private String repe_id; //库存编号
    private String pact_state; //有效，已终止
    private String customer_name;
    private String customer_classify;
    private String customer_id_card;
    private String rent_address; //签约地址
    private String begin_time;
    private String end_time;
    private float rent; //租金
    private float deposit; //押金
    private String customer_tel;
    private String customer_address;

    public String getRepe_id() {
        return repe_id;
    }

    public void setRepe_id(String repe_id) {
        this.repe_id = repe_id;
    }

    public String getPact_state() {
        return pact_state;
    }

    public void setPact_state(String pact_state) {
        this.pact_state = pact_state;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_classify() {
        return customer_classify;
    }

    public void setCustomer_classify(String customer_classify) {
        this.customer_classify = customer_classify;
    }

    public String getCustomer_id_card() {
        return customer_id_card;
    }

    public void setCustomer_id_card(String customer_id_card) {
        this.customer_id_card = customer_id_card;
    }

    public String getRent_address() {
        return rent_address;
    }

    public void setRent_address(String rent_address) {
        this.rent_address = rent_address;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public String getCustomer_tel() {
        return customer_tel;
    }

    public void setCustomer_tel(String customer_tel) {
        this.customer_tel = customer_tel;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }
}
