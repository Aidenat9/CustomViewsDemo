package me.tmgg.viewsdemoapp.bean;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/6/18 23:15
 * package：me.tmgg.viewsdemoapp.bean
 * version：1.0
 * <p>description：              </p>
 */
public class TabEntity {
    private String title;
    private boolean checked;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
