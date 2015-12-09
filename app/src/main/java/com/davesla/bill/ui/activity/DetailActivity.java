package com.davesla.bill.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.davesla.bill.R;

public class DetailActivity extends BaseActivity {

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_detail);
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        context.startActivity(intent);
    }
}
