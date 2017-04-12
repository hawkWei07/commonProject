package cn.hawk.commonproject.ui.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.common.Constants;
import cn.hawk.commonproject.contracts.PoetryDetailContract;
import cn.hawk.commonproject.presents.PoetryDetailPresenter;

/**
 * Created by kehaowei on 2017/4/12.
 */

public class PoetryDetailActivity extends BaseActivity implements PoetryDetailContract.View {
    private PoetryDetailContract.Presenter mPresenter;

    private int poetryId;
    private String poetryTitle;
    private String poetryAuthor;
    private String poetryTime;

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView title;
    TextView time;
    TextView author;
    TextView content;

    @Override
    protected void bindView() {
        super.bindView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.poetry_title);
        time = (TextView) findViewById(R.id.poetry_time);
        author = (TextView) findViewById(R.id.poetry_author);
        content = (TextView) findViewById(R.id.poetry_content);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        poetryId = intent.getIntExtra(Constants.EXTRA_KEY_ID, -1);
        if (poetryId < 0) {
            Toast.makeText(this, getString(R.string.alert_poetry_not_exists), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        poetryTitle = intent.getStringExtra(Constants.EXTRA_KEY_TITLE);
        poetryAuthor = intent.getStringExtra(Constants.EXTRA_KEY_AUTHOR);
        poetryTime = intent.getStringExtra(Constants.EXTRA_KEY_TIME);
        mPresenter = new PoetryDetailPresenter(this, this, poetryId);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (!TextUtils.isEmpty(poetryTitle)) {
            collapsingToolbarLayout.setTitle(poetryTitle);
            title.setText(poetryTitle);
        }
        if (!TextUtils.isEmpty(poetryAuthor))
            author.setText(poetryAuthor);
        if (!TextUtils.isEmpty(poetryTime))
            time.setText(poetryTime);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_poetry_detail;
    }

    @Override
    public void setPresenter(PoetryDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPoetryContent(String content) {
        if (TextUtils.isEmpty(content))
            return;
        if (null != this.content) {
            this.content.setText(content);
        }
    }
}
