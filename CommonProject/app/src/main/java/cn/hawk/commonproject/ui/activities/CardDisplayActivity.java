package cn.hawk.commonproject.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.presents.CardDisplayPresenter;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayActivity extends BaseActivity implements CardDisplayContract.View {
    Toolbar toolbar;
    private CardDisplayContract.Presenter mPresenter;

    @Override
    protected int getContentId() {
        return R.layout.activity_base;
    }

    @Override
    protected void bindView() {
        super.bindView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new CardDisplayPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public void setPresenter(CardDisplayContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_display_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
