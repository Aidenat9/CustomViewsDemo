package me.tmgg.viewsdemoapp.bean;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/6/5 21:47
 * package：me.tmgg.viewsdemoapp.bean
 * version：1.0
 * <p>description：    test diffutil          </p>
 */
public class TestBean implements Cloneable{
    private String name;
    private String desc;
    private int id;

    public TestBean(String name, String desc, int id) {
        this.name = name;
        this.desc = desc;
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 实现了clone方法，仅用于写Demo模拟刷新用，实际项目不需要，因为刷新时，数据都是从网络拉取的。:
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public TestBean clone() throws CloneNotSupportedException {
        TestBean bean =null;
        try {
            bean = (TestBean) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
