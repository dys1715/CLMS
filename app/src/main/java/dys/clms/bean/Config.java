package dys.clms.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/26 0026.
 */
public class Config {
    private String configTitle;
    private ArrayList<String> config;

    public Config(String configTitle, ArrayList<String> config) {
        this.configTitle = configTitle;
        this.config = config;
    }

    public String getConfigTitle() {
        return configTitle;
    }

    public void setConfigTitle(String configTitle) {
        this.configTitle = configTitle;
    }

    public ArrayList<String> getConfig() {
        return config;
    }

    public void setConfig(ArrayList<String> config) {
        this.config = config;
    }
}
