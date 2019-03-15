package com.example.bob.health_helper.Data.Bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "like")
public class Like {
    @DatabaseField(useGetSet = true,columnName = "id",dataType = DataType.INTEGER,generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true,columnName = "uid",dataType = DataType.STRING,canBeNull = false)
    private String uid;
    @DatabaseField(useGetSet = true,columnName = "answerId",dataType = DataType.INTEGER,canBeNull = false)
    private int answerId;

    public Like(){}

    public Like(String uid, int answerId) {
        this.uid = uid;
        this.answerId = answerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
