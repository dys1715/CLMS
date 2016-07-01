package dys.clms.bean;

/**
 * Created by dys on 2016/7/1 0001.
 */
public class Company {
    private String name;
    private String tel;
    private String address;

    public Company(String name, String tel, String address) {
        this.name = name;
        this.tel = tel;
        this.address = address;
    }

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
