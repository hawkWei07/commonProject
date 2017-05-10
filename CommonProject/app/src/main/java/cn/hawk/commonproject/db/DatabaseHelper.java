package cn.hawk.commonproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/5/10.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    protected static String DB_NAME = "commonDB";

    protected static int DB_VERSION = 1;

    protected Map<String, Dao> daos = new HashMap<>();

    private static DatabaseHelper instance;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    public void initDbs(ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PoetryItemBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类来获得指定的Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        initDbs(connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        dropDbs(connectionSource);
        initDbs(connectionSource);
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (null == instance) {
            synchronized (DatabaseHelper.class) {
                if (null == instance) {
                    instance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void dropDbs(ConnectionSource connectionSource) {
        try {
            TableUtils.dropTable(connectionSource, PoetryItemBean.class, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
