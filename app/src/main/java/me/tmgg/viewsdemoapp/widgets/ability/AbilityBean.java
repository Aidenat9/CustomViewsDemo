package me.tmgg.viewsdemoapp.widgets.ability;

public class AbilityBean {
   private float value;
   private String desc;

    public AbilityBean(float value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDesc() {
        return desc == null ? "" : desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}