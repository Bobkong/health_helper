package com.example.bob.health_helper.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bob.health_helper.Local.LocalBean.Favorite;
import com.example.bob.health_helper.Local.LocalBean.Like;
import com.example.bob.health_helper.Local.LocalBean.SearchHistory;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME="health_helper.db";
    private static final int DB_VERSION=1;

    private DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    //单例模式
    private static DatabaseHelper instance;
    public static synchronized DatabaseHelper getInstance(Context context){
        context=context.getApplicationContext();
        if(instance==null){
            synchronized (DatabaseHelper.class){
                if(instance==null)
                    instance=new DatabaseHelper(context);
            }
        }
        return instance;
    }

    //建数据库
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTableIfNotExists(connectionSource, Favorite.class);
            TableUtils.createTableIfNotExists(connectionSource, Like.class);
            TableUtils.createTableIfNotExists(connectionSource, SearchHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,Favorite.class,true);
            TableUtils.dropTable(connectionSource,Like.class,true);
            TableUtils.dropTable(connectionSource,SearchHistory.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
