package cn.hawk.commonproject.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hawk.commonlib.base.MVPActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.WriteQRCodeContract;
import cn.hawk.commonproject.presents.WriteQRCodeActivityPresenter;

/**
 * Created by kehaowei on 2017/6/26.
 */

public class WriteQRCodeActivity extends MVPActivity<WriteQRCodeActivityPresenter>
        implements WriteQRCodeContract.View, View.OnClickListener {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_generate)
    Button btnGenerate;
    @BindView(R.id.iv_code)
    ImageView ivCode;

    @Override
    public WriteQRCodeActivityPresenter createPresenter() {
        return new WriteQRCodeActivityPresenter(this, this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_write_qrcode;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        btnGenerate.setOnClickListener(this);
    }

    @Override
    public void onCodeGenerateSuccess(Bitmap bitmap) {
        if (null == ivCode)
            return;
        ivCode.setImageBitmap(bitmap);
        ivCode.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    public void onCodeGenerateFailed(String msg) {
        showToast(msg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_generate:
                generateCode();
                break;
        }
    }

    private void generateCode() {
        if (null == etContent || TextUtils.isEmpty(etContent.getText().toString()))
            return;
        mPresenter.generateQRCode(etContent.getText().toString().trim());
    }
}
