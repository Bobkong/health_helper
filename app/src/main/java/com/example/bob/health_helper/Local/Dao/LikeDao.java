package com.example.bob.health_helper.Local.Dao;

import android.content.Context;

import com.example.bob.health_helper.Local.LocalBean.Like;
import com.example.bob.health_helper.Local.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class LikeDao {
    private Dao<Like,Integer> likeDao;
    private DatabaseHelper helper;

    public LikeDao(Context context){
        try{
            helper=DatabaseHelper.getInstance(context);
            likeDao=helper.getDao(Like.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLike(Like like){
        try{
            likeDao.create(like);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLike(Like like){
        try{
            likeDao.delete(like);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Like> queryIsLike(String uid, int answerId){
        try{
           return likeDao.queryBuilder()
                    .where()
                    .eq("uid",uid).and()
                    .eq("answerId",answerId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
