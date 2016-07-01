package dys.clms.bean;

/**
 * Created by dys on 2016/6/28 0028.
 */
public class Customer {
    private String classify;
    private int rate;

    public Customer(String classify, int rate) {
        this.classify = classify;
        this.rate = rate;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
