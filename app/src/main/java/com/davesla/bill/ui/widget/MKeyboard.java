package com.davesla.bill.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davesla.bill.R;

/**
 * Created by hwb on 15/12/11.
 */
public class MKeyboard extends RelativeLayout implements View.OnClickListener {
    private LinearLayout layoutKeyboard;
    private TextView btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    private ImageView btnDelete, btnDone;

    private OnInputListener onInputListener;

    public MKeyboard(Context context) {
        this(context, null);
    }

    public MKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.keyboard, null, false);
        btnOne = (TextView) view.findViewById(R.id.btn_one);
        btnTwo = (TextView) view.findViewById(R.id.btn_two);
        btnThree = (TextView) view.findViewById(R.id.btn_three);
        btnFour = (TextView) view.findViewById(R.id.btn_four);
        btnFive = (TextView) view.findViewById(R.id.btn_five);
        btnSix = (TextView) view.findViewById(R.id.btn_six);
        btnSeven = (TextView) view.findViewById(R.id.btn_seven);
        btnEight = (TextView) view.findViewById(R.id.btn_eight);
        btnNine = (TextView) view.findViewById(R.id.btn_nine);
        btnZero = (TextView) view.findViewById(R.id.btn_zero);
        btnDelete = (ImageView) view.findViewById(R.id.btn_delete);
        btnDone = (ImageView) view.findViewById(R.id.btn_done);
        layoutKeyboard = (LinearLayout) view.findViewById(R.id.layout_keyboard);


        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZero.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnDone.setOnClickListener(this);


        layoutKeyboard.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 220 * 4));

        this.addView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                if (onInputListener != null) {
                    onInputListener.onDelete();
                }
                break;
            case R.id.btn_done:
                if (onInputListener != null) {
                    onInputListener.onFinish();
                }
                break;
            default:
                if (onInputListener != null) {
                    onInputListener.onInput(((TextView) v).getText().charAt(0));
                }
        }
    }

    public interface OnInputListener {
        void onInput(char ch);

        void onDelete();

        void onFinish();
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

}
