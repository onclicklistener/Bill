package com.davesla.bill.ui.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.davesla.bill.R;

public class HomeActivity extends BaseActivity {

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailActivity.start(HomeActivity.this);
            }
        });

    }


}
