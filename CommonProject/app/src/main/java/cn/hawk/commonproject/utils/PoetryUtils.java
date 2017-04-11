package cn.hawk.commonproject.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import cn.hawk.commonlib.utils.StringUtils;
import cn.hawk.commonproject.models.BaseJsonBean;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;

/**
 * Created by kehaowei on 2017/4/11.
 */

public class PoetryUtils {

    public static final String POETRIES_ASSETS_NAME = "poetrydata.txt";

    public static PoetriesListBean getAllPoetries(Context context) {
        return getDefaultPoetries(context);
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
        return body.getData();
    }

}
