package com.example.bob.health_helper.Data.Dao;

import android.content.Context;

import com.example.bob.health_helper.Data.Bean.Favorite;
import com.example.bob.health_helper.Data.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class FavoriteDao {
    private Dao<Favorite,Integer> favoriteDao;
    private DatabaseHelper helper;

    public FavoriteDao(Context context){
        try{
            helper=DatabaseHelper.getInstance(context);
            favoriteDao=helper.getDao(Favorite.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFavorite(Favorite favorite){
        try{
            favoriteDao.create(favorite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFavorite(Favorite favorite){
        try{
            favoriteDao.delete(favorite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Favorite> queryIsFavorite(String uid, int questionId){
        try{
             return favoriteDao.queryBuilder()
                    .where()
                    .eq("uid",uid).and()
                    .eq("questionId",questionId).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
