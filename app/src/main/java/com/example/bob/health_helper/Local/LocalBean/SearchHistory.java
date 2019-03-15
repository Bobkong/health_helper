package com.example.bob.health_helper.Local.LocalBean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "search_history")
public class SearchHistory {
    @DatabaseField(useGetSet = true,columnName = "id",dataType = DataType.INTEGER,generatedId = true)
    private int id;
    @DatabaseField(useGetSet = true,columnName = "text",dataType = DataType.STRING,canBeNull = false)
    private String text;

    public SearchHistory(){}

    public SearchHistory(String text){
        this.text=text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
