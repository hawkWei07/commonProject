package cn.hawk.commonproject.ui.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.ysten.interfacedef.interfaces.IHLSample;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hawk.commonproject.R;
import dalvik.system.DexClassLoader;

/**
 * Created by kehaowei on 2017/7/11.
 */

public class HLActivity extends AppCompatActivity {
    private static final String PATH = "/sdcard/test.jar";
    private static final String LIB_NAME = "com.ysten.interfaceimpl.impl.HLSampleImpl";
    @BindView(R.id.btn_show_info)
    Button btnShowInfo;

    private IHLSample lib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hl);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick(R.id.btn_show_info)
    public void onViewClicked() {
        if (null != lib) {
            lib.showInfo();
        } else {
            Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        DexClassLoader classLoader = new DexClassLoader(PATH, getCacheDir().getAbsolutePath(), null, getClassLoader());
        try {
            Class clib = classLoader.loadClass(LIB_NAME);
            if (null != clib) {
                lib = (IHLSample) clib.newInstance();
                lib.init(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
