package com.davesla.bill.ui.activity;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.davesla.bill.R;
import com.davesla.bill.bean.event.EmptyEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by hwb on 15/12/8.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContent();
        initView();
        initData();
    }

    protected abstract void setContent();

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


    public void showToast(int id) {
        String content = getResources().getString(id);
        showToast(content);
    }

    public void showLongToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public void showLongToast(int id) {
        String content = getResources().getString(id);
        showLongToast(content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EmptyEvent event) {

    }

    /**
     * 将状态栏设置为colorPrimaryDark的颜色
     */
    protected void colorStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            final Resources.Theme theme = getTheme();
            TypedArray a = theme.obtainStyledAttributes(R.styleable.Theme);
            int color = a.getColor(R.styleable.Theme_colorPrimaryDark, 0);
            a.recycle();
            window.setStatusBarColor(color);
        }
    }

    /**
     * 将状态栏设置为黑色
     */
    protected void blackStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.BLACK);
        }
    }

    protected void setStatusBarColor(int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    public int getResourceColor(int color) {
        return getResources().getColor(color);
    }

}
