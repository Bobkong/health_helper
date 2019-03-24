package com.example.bob.health_helper.Local.LocalBean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "reminder")
public class Reminder implements Serializable {
    @DatabaseField(useGetSet = true,columnName = "id",dataType = DataType.INTEGER,generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true,columnName = "hour",dataType = DataType.INTEGER)
    private int hour;
    @DatabaseField(useGetSet = true,columnName = "minute",dataType = DataType.INTEGER)
    private int minute;
    @DatabaseField(useGetSet = true,columnName = "type",dataType = DataType.STRING)
    private String type;
    @DatabaseField(useGetSet = true,columnName = "content",dataType = DataType.STRING)
    private String content;
    @DatabaseField(useGetSet = true,columnName = "repeat",dataType = DataType.INTEGER)
    private int repeat;
    @DatabaseField(useGetSet = true,columnName = "set",dataType = DataType.BOOLEAN)
    private boolean enable;

    public Reminder(){}

    public Reminder(int hour, int minute, String type, String content, int repeat, boolean enable) {
        this.hour = hour;
        this.minute = minute;
        this.type = type;
        this.content = content;
        this.repeat = repeat;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", hour=" + hour +
                ", minute=" + minute +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", repeat=" + repeat +
                ", enable=" + enable +
                '}';
    }
}
