package dys.clms.bean;

/**
 * Created by dys on 2016/6/28 0028.
 */
public class Customer {
    private String classify;
    private String discountRate;

    public Customer(String classify, String discountRate) {
        this.classify = classify;
        this.discountRate = discountRate;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }
}
