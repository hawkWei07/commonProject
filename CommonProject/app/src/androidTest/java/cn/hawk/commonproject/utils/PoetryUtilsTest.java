package cn.hawk.commonproject.utils;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.widget.Toast;

import org.junit.Test;

import cn.hawk.commonlib.utils.LogUtils;
import cn.hawk.commonproject.AppContext;
import cn.hawk.commonproject.models.PoetriesListBean;
import cn.hawk.commonproject.models.PoetryItemBean;

import static org.junit.Assert.*;

/**
 * Created by kehaowei on 2017/4/11.
 */
public class PoetryUtilsTest {
    private static final String TAG = PoetryUtilsTest.class.getSimpleName();

    @Test
    public void getAllPoetries() throws Exception {
        PoetriesListBean bean = PoetryUtils.getAllPoetries(InstrumentationRegistry.getInstrumentation().getContext());
        for (PoetryItemBean item : bean.getPoetries()) {
            AppContext.getInstance().logd(TAG, item.toString());
        }
    }

    @Test
    public void getPoetryById() throws Exception {
        PoetryItemBean bean = PoetryUtils.getPoetryById(InstrumentationRegistry.getInstrumentation().getContext(), 20);
        if (null != bean) {
            AppContext.getInstance().logd(TAG, bean.toString());
        } else {
            AppContext.getInstance().logd(TAG, "Poetry not exists");
        }
    }

}