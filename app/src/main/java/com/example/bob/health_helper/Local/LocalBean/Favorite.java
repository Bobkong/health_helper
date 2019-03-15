package com.example.bob.health_helper.Local.LocalBean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Favorite")
public class Favorite {
    @DatabaseField(useGetSet = true,columnName = "id",dataType = DataType.INTEGER,generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true,columnName = "uid",dataType = DataType.STRING,canBeNull = false)
    private String uid;
    @DatabaseField(useGetSet = true,columnName = "questionId",dataType = DataType.INTEGER,canBeNull = false)
    private int questionId;

    public Favorite(){}

    public Favorite(String uid, int questionId) {
        this.uid = uid;
        this.questionId = questionId;
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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
