package dys.clms.bean.db;

import org.litepal.crud.DataSupport;

import dys.clms.bean.db.config.Box;
import dys.clms.bean.db.config.CDDriver;
import dys.clms.bean.db.config.CPU;
import dys.clms.bean.db.config.GPU;
import dys.clms.bean.db.config.HardDisk;
import dys.clms.bean.db.config.Keyboard;
import dys.clms.bean.db.config.Mainboard;
import dys.clms.bean.db.config.Memory;
import dys.clms.bean.db.config.Mouse;
import dys.clms.bean.db.config.NetworkCard;
import dys.clms.bean.db.config.Screen;
import dys.clms.bean.db.config.SoftDriver;
import dys.clms.bean.db.config.SoundBox;
import dys.clms.bean.db.config.SoundCard;

/**
 * Created by dys on 2016/6/29 0029.
 * 常用配置表
 */
public class Configurations extends DataSupport {
    private long id;
    private CPU mCPU;
    private Memory mMemory;
    private HardDisk mHardDisk;
    private GPU mGPU;
    private Screen mScreen;
    private CDDriver mCDDriver;
    private SoftDriver mSoftDriver;
    private SoundCard mSoundCard;
    private Keyboard mKeyboard;
    private Mouse mMouse;
    private Box mBox;
    private SoundBox mSoundBox;
    private NetworkCard mNetworkCard;
    private Mainboard mMainboard;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CPU getCPU() {
        return mCPU;
    }

    public void setCPU(CPU CPU) {
        mCPU = CPU;
    }

    public Memory getMemory() {
        return mMemory;
    }

    public void setMemory(Memory memory) {
        mMemory = memory;
    }

    public HardDisk getHardDisk() {
        return mHardDisk;
    }

    public void setHardDisk(HardDisk hardDisk) {
        mHardDisk = hardDisk;
    }

    public GPU getGPU() {
        return mGPU;
    }

    public void setGPU(GPU GPU) {
        mGPU = GPU;
    }

    public Screen getScreen() {
        return mScreen;
    }

    public void setScreen(Screen screen) {
        mScreen = screen;
    }

    public CDDriver getCDDriver() {
        return mCDDriver;
    }

    public void setCDDriver(CDDriver CDDriver) {
        mCDDriver = CDDriver;
    }

    public SoftDriver getSoftDriver() {
        return mSoftDriver;
    }

    public void setSoftDriver(SoftDriver softDriver) {
        mSoftDriver = softDriver;
    }

    public SoundCard getSoundCard() {
        return mSoundCard;
    }

    public void setSoundCard(SoundCard soundCard) {
        mSoundCard = soundCard;
    }

    public Keyboard getKeyboard() {
        return mKeyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        mKeyboard = keyboard;
    }

    public Mouse getMouse() {
        return mMouse;
    }

    public void setMouse(Mouse mouse) {
        mMouse = mouse;
    }

    public Box getBox() {
        return mBox;
    }

    public void setBox(Box box) {
        mBox = box;
    }

    public SoundBox getSoundBox() {
        return mSoundBox;
    }

    public void setSoundBox(SoundBox soundBox) {
        mSoundBox = soundBox;
    }

    public NetworkCard getNetworkCard() {
        return mNetworkCard;
    }

    public void setNetworkCard(NetworkCard networkCard) {
        mNetworkCard = networkCard;
    }

    public Mainboard getMainboard() {
        return mMainboard;
    }

    public void setMainboard(Mainboard mainboard) {
        mMainboard = mainboard;
    }
}
