package cn.hawk.commonproject.ui.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cn.hawk.commonlib.base.BaseActivity;
import cn.hawk.commonlib.interfaces.OnItemClickListener;
import cn.hawk.commonproject.R;
import cn.hawk.commonproject.adapters.PoetryListAdapter;
import cn.hawk.commonproject.adapters.StaggeredHomeAdapter;
import cn.hawk.commonproject.contracts.CardDisplayContract;
import cn.hawk.commonproject.models.PoetryItemBean;
import cn.hawk.commonproject.presents.CardDisplayPresenter;

/**
 * Created by kehaowei on 2017/4/10.
 */

public class CardDisplayActivity extends BaseActivity implements CardDisplayContract.View, OnItemClickListener {
    private CardDisplayContract.Presenter mPresenter;

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    NavigationView navView;
    FloatingActionButton fab;
    RecyclerView recyclerView;

    PoetryListAdapter mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_base;
    }

    @Override
    protected void bindView() {
        super.bindView();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.rv_poetry_list);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new CardDisplayPresenter(this, this);
        mAdapter = new PoetryListAdapter(this, new ArrayList<PoetryItemBean>());
        mAdapter.setmListener(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_author);
        // 禁用图标渲染
        navView.setItemIconTintList(null);

        initRecyclerView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.alert_data_deleted, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(CardDisplayActivity.this, R.string.alert_data_restored, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
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
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private void initRecyclerView() {
        if (null != recyclerView) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setAdapter(mAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void showPoetries(ArrayList<PoetryItemBean> infos) {
        if (null == mAdapter) {
            mAdapter = new PoetryListAdapter(this, infos);
            mAdapter.setmListener(this);
        } else {
            mAdapter.refresh(infos);
        }
    }
}
