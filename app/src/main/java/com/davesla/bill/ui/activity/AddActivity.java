package com.davesla.bill.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.davesla.bill.R;
import com.davesla.bill.service.BillService;
import com.davesla.bill.service.bean.Bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddActivity extends BaseActivity {
    private List<String> userList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();
    private TextView textDate, textSave;
    private EditText editCost, editRemarks;
    private ImageView imageAvatar, imageCategory;
    private AppCompatSpinner spinnerAvatar, spinnerCategory;

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_add);
    }

    @Override
    protected void initView() {
        textSave = (TextView) findViewById(R.id.tv_save);
        textDate = (TextView) findViewById(R.id.tv_date);
        editCost = (EditText) findViewById(R.id.et_cost);
        editRemarks = (EditText) findViewById(R.id.et_remarks);
        spinnerAvatar = (AppCompatSpinner) findViewById(R.id.spinner_user);
        spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinner_category);
        imageAvatar = (ImageView) findViewById(R.id.iv_avatar);
        imageCategory = (ImageView) findViewById(R.id.iv_category);
    }

    @Override
    protected void initData() {
        colorStatusBar();
        editCost.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
        userList.add("何卫兵");
        userList.add("王正昕");
        userList.add("封光");
        categoryList.add("生活用品");
        categoryList.add("纯净水");
        categoryList.add("水费");
        categoryList.add("食品");
        categoryList.add("电费");
        categoryList.add("燃气费");
        categoryList.add("其它");
        spinnerAvatar.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, userList));
        spinnerCategory.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categoryList));

        editRemarks.setText(categoryList.get(0));

        setDate();

        spinnerAvatar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String user = userList.get(position);
                if (user.equals(userList.get(0))) {
                    imageAvatar.setImageResource(R.drawable.ic_avatar_bing);
                } else if (user.equals(userList.get(1))) {
                    imageAvatar.setImageResource(R.drawable.ic_avatar_xin);
                } else {
                    imageAvatar.setImageResource(R.drawable.ic_avatar_feng);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = categoryList.get(position);
                editRemarks.setText(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        textSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editCost.getText() == null) {
                    showToast("请输入金额");
                    return;
                }
                save();
            }
        });


    }

    private void save() {
        Bill bill = new Bill();
        bill.setCost(Float.parseFloat(editCost.getText().toString()));
        bill.setUserName(userList.get(spinnerAvatar.getSelectedItemPosition()));
        bill.setCategoryName(categoryList.get(spinnerCategory.getSelectedItemPosition()));
        bill.setDetail(editRemarks.getText().toString());
        bill.setClearDate(new Date(-28800000));
        BillService.addBill(bill, new BillService.OnAddBillHandler() {
            @Override
            public void onSucceed() {
                showToast("添加成功");
                finish();
            }

            @Override
            public void onFailed(AVException e) {
                System.out.println("exception:" + e);
            }
        });
    }

    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String today = null;
        if (day == 2) {
            today = "Monday";
        } else if (day == 3) {
            today = "Tuesday";
        } else if (day == 4) {
            today = "Wednesday";
        } else if (day == 5) {
            today = "Thursday";
        } else if (day == 6) {
            today = "Friday";
        } else if (day == 7) {
            today = "Saturday";
        } else if (day == 1) {
            today = "Sunday";
        }
        int month = (calendar.get(Calendar.MONTH) + 1);
        String mon = null;
        switch (month) {
            case 1:
                mon = "January";
                break;
            case 2:
                mon = "February";
                break;
            case 3:
                mon = "March";
                break;
            case 4:
                mon = "April";
                break;
            case 5:
                mon = "May";
                break;
            case 6:
                mon = "June";
                break;
            case 7:
                mon = "July";
                break;
            case 8:
                mon = "August";
                break;
            case 9:
                mon = "September";
                break;
            case 10:
                mon = "October";
                break;
            case 11:
                mon = "November";
                break;
            case 12:
                mon = "December";
                break;
        }
        String minute = String.format(Locale.ENGLISH, "%02d", calendar.get(Calendar.MINUTE));
        ;
        textDate.setText(today + ","
                + mon + " "
                + calendar.get(Calendar.DAY_OF_MONTH) + ","
                + calendar.get(Calendar.YEAR) + ","
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + minute);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AddActivity.class);
        context.startActivity(intent);
    }
}
