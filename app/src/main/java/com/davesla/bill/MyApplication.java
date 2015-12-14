package com.davesla.bill;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.davesla.bill.service.bean.Bill;

public class MyApplication extends Application {
	private static MyApplication instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		AVObject.registerSubclass(Bill.class);
		AVOSCloud.initialize(this,
                "NG3mBf1vG3KmBNe82GN5LvQe-gzGzoHsz", "nR08reYYhGLychyTQMzFowf5");

	}

	public static MyApplication getInstance() {
		return instance;
	}

}
