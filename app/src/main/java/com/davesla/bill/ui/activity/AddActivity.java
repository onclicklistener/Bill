package com.davesla.bill.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.davesla.bill.R;

public class AddActivity extends BaseActivity {

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_add);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        colorStatusBar();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        context.startActivity(intent);
    }
}
