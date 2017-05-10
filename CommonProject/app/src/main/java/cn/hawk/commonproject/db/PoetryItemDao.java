package cn.hawk.commonproject.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hawk.commonlib.db.BaseDao;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/5/10.
 */

public class PoetryItemDao extends BaseDao<PoetryItemBean, DatabaseHelper> {

    public PoetryItemDao(Context context) {
        super(context);
    }

    @Override
    public Dao getDao() {
        try {
            return mHelper.getDao(PoetryItemBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DatabaseHelper getHelper(Context context) {
        return DatabaseHelper.getInstance(context);
    }

    public List<PoetryItemBean> getAll() {
        List<PoetryItemBean> result = null;
        try {
            result = mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void saveAll(ArrayList<PoetryItemBean> list) {
        try {
            AppContext.getInstance().logd("DAO", "try to save");
            mDao.create(list);
        } catch (SQLException e) {
            e.printStackTrace();
            AppContext.getInstance().loge("DAO", "error : " + e.getLocalizedMessage());
        }
    }

    public PoetryItemBean getPoetryById(int id) {
        PoetryItemBean result = null;
        try {
            result = mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
