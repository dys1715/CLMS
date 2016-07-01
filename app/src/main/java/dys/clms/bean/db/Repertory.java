package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by dys on 2016/6/29 0029.
 * 库存表
 */
public class Repertory extends DataSupport {
    private int repe_classify_name;  //笔记本、手机……
    private int rent_state;     //0：未出租，1：已出租
    private float rent;         //租金
    private float deposit;      //押金
    private String cpu;
    private String memory;
    private String hardDisk;
    private String gpu;
    private String screen;
    private String cdDriver;
    private String softDriver;
    private String soundCard;
    private String keyboard;
    private String mouse;
    private String box;
    private String soundBox;
    private String networkCard;
    private String mainboard;

    public int getRepe_classify_name() {
        return repe_classify_name;
    }

    public void setRepe_classify_name(int repe_classify_name) {
        this.repe_classify_name = repe_classify_name;
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCdDriver() {
        return cdDriver;
    }

    public void setCdDriver(String cdDriver) {
        this.cdDriver = cdDriver;
    }

    public String getSoftDriver() {
        return softDriver;
    }

    public void setSoftDriver(String softDriver) {
        this.softDriver = softDriver;
    }

    public String getSoundCard() {
        return soundCard;
    }

    public void setSoundCard(String soundCard) {
        this.soundCard = soundCard;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getSoundBox() {
        return soundBox;
    }

    public void setSoundBox(String soundBox) {
        this.soundBox = soundBox;
    }

    public String getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(String networkCard) {
        this.networkCard = networkCard;
    }

    public String getMainboard() {
        return mainboard;
    }

    public void setMainboard(String mainboard) {
        this.mainboard = mainboard;
    }
}
