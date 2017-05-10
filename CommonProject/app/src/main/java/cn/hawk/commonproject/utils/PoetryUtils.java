package cn.hawk.commonproject.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonlib.utils.StringUtils;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.db.PoetryItemDao;
import cn.hawk.commonproject.models.BaseJsonBean;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryUtils {

    public static final String POETRIES_ASSETS_NAME = "poetrydata.txt";

    public static PoetriesListBean getAllPoetries(Context context) {
        PoetryItemDao dao = new PoetryItemDao(context);
        List<PoetryItemBean> list = dao.getAll();
        if (null == list || list.size() == 0) {
            AppContext.getInstance().logd("UTIL", "get from asset");
            return getDefaultPoetries(context);
        } else {
            AppContext.getInstance().logd("DB", "get from db");
            PoetriesListBean result = new PoetriesListBean();
            result.setPoetries(list);
            return result;
        }
    }

    private static PoetriesListBean getDefaultPoetries(Context context) {
        String res = StringUtils.readAssertResource(context, POETRIES_ASSETS_NAME);
        if (TextUtils.isEmpty(res))
            return null;
        BaseJsonBean<PoetriesListBean> body;
        Gson gson = new Gson();
        Type poetryType = new TypeToken<BaseJsonBean<PoetriesListBean>>() {
        }.getType();
        body = gson.fromJson(res, poetryType);
        PoetriesListBean bean = body.getData();
        AppContext.getInstance().logd("UTIL", "get default : " + bean + " | " + bean.getPoetries().size());
        if (null != bean && bean.getPoetries().size() > 0) {
            PoetryItemDao dao = new PoetryItemDao(context);
            dao.saveAll(bean.getPoetries());
        }
        return bean;
    }

    public static PoetryItemBean getPoetryById(Context context, int id) {
        PoetryItemDao dao = new PoetryItemDao(context);
        PoetryItemBean item = dao.getPoetryById(id);
        if (null != item) {
            AppContext.getInstance().logd("DB", "get poetry by id from db");
            return item;
        }
        PoetriesListBean body = getDefaultPoetries(context);
        if (null == body)
            return null;
        AppContext.getInstance().logd("UTIL", "get poetry by id from asset");
        return body.getPoetryById(id);
    }

}
