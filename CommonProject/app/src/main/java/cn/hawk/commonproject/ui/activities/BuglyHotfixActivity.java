package cn.hawk.commonproject.ui.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;

/**
 * Created by kehaowei on 2017/8/28.
 */

public class BuglyHotfixActivity extends BaseActivity {

    @BindView(R.id.btn_toast)
    Button btnToast;

    @Override
    protected int getContentId() {
        return R.layout.activity_bugly_hotfix;
    }

    @OnClick(R.id.btn_toast)
    public void onViewClicked() {
        showToast();
    }

    private void showToast() {
        Toast.makeText(this, "这是修复版本", Toast.LENGTH_SHORT).show();
    }
}
