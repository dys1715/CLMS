package dys.clms.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/26 0026.
 */
public class Config {
    private String configTitle;
    private List<String> config;

    public Config(String configTitle, List<String> config) {
        this.configTitle = configTitle;
        this.config = config;
    }

    public String getConfigTitle() {
        return configTitle;
    }

    public void setConfigTitle(String configTitle) {
        this.configTitle = configTitle;
    }

    public List<String> getConfig() {
        return config;
    }

    public void setConfig(List<String> config) {
        this.config = config;
    }
}
