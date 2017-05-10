package cn.hawk.commonlib.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

/**
 * Created by kehaowei on 2017/5/10.
 */

public abstract class BaseDao<T, D> {
    protected Dao<T, Integer> mDao;
    protected D mHelper;

    public BaseDao(Context context) {
        try {
            mHelper = getHelper(context);
            mDao = getDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Dao getDao();

    public abstract D getHelper(Context context);
}
