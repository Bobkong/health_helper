package com.example.bob.health_helper.Local.Dao;

import android.content.Context;

import com.example.bob.health_helper.Local.LocalBean.SearchHistory;
import com.example.bob.health_helper.Local.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class SearchHistoryDao {
    private Dao<SearchHistory,Integer> searchHistoryDao;
    private DatabaseHelper helper;

    public SearchHistoryDao(Context context){
        try{
            helper=DatabaseHelper.getInstance(context);
            searchHistoryDao=helper.getDao(SearchHistory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHistory(SearchHistory history){
        try{
            searchHistoryDao.create(history);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHistoryById(SearchHistory history){
        try{
            searchHistoryDao.delete(history);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchHistory> queryAllSearchHistory(){
        try{
            return searchHistoryDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
