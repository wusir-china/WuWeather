package com.wusir.database;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.wusir.wuweather.MyApplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zy on 2018/2/6.
 */

public class GeneralDao<T>{
    private static final String TAG = "GeneralDao";
    private DataBaseHelper dbHelper;
    private Context context;

    public GeneralDao() {
        context = MyApplication.AppContext;
        dbHelper = DataBaseHelper.getInstance(context);
    }

    /**
     * 新增一条记录
     */
    public int create(T po) {
        try {
            Dao dao = dbHelper.getDao(po.getClass());
            return dao.create(po);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean exists(T po, Map<String, Object> where) {
        try {
            Dao dao = dbHelper.getDao(po.getClass());
            if (dao.queryForFieldValues(where).size() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int createIfNotExists(T po, Map<String, Object> where) {
        try {
            Dao dao = dbHelper.getDao(po.getClass());
            if (dao.queryForFieldValues(where).size() < 1) {
                return dao.create(po);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询一条记录
     */
    public List<T> queryForEq(Class<T> c, String fieldName, Object value) {
        try {
            Dao dao = dbHelper.getDao(c);
            return dao.queryForEq(fieldName, value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /**
     * 查询第一条记录
     */
    public T queryForFirst(Class<T> c) {
        try {
            Dao dao = dbHelper.getDao(c);
            QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
            return queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据id删除一条记录
     *
     * @param po
     * @param id
     * @return
     */
    public int removeById(T po, int id) {
        try {
            Dao dao = dbHelper.getDao(po.getClass());
            return dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 根据特定条件更新特定字段
     *
     * @param c
     * @param values
     * @param columnName where字段
     * @param value      where值
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int update(Class<T> c, ContentValues values, String columnName, Object value) {
        try {
            Dao dao = dbHelper.getDao(c);
            UpdateBuilder<T, Long> updateBuilder = dao.updateBuilder();
            updateBuilder.where().eq(columnName, value);
            for (String key : values.keySet()) {
                updateBuilder.updateColumnValue(key, values.get(key));
            }
            return updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 更新一条记录
     */
    public int update(T po) {
        try {
            Dao dao = dbHelper.getDao(po.getClass());
            return dao.update(po);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 查询所有记录
     */
    public List<T> queryForAll(Class<T> c) {
        try {
            Dao dao = dbHelper.getDao(c);
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }
}
