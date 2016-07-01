package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/7/1 0001.
 */
public class CompanyInfo extends DataSupport {
    private String name;
    private String tel;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
