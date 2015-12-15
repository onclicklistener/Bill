package com.davesla.bill.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davesla.bill.R;
import com.davesla.bill.service.bean.BillGroup;

import java.util.ArrayList;

/**
 * Created by hwb on 15/12/14.
 */
public class BillWidget extends LinearLayout {
    public BillWidget(Context context) {
        this(context, null);
    }

    public BillWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BillWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public void setup(BillGroup billGroup) {
        ArrayList<Double> costs = billGroup.costs;
        ArrayList<String> users = billGroup.users;
        double totalCost = 0d;
        ArrayList<Money> moneys = new ArrayList<>();
        for (int i = 0; i < costs.size(); i++) {
            Money money = new Money();
            money.name = users.get(i);
            money.cost = costs.get(i);
            moneys.add(money);

            totalCost += costs.get(i);
        }
        double aveCost = totalCost / billGroup.groupMembers.length;
        ArrayList<Money> highCosts = new ArrayList<>();
        ArrayList<Money> lowCosts = new ArrayList<>();
        for (Money money : moneys) {
            if (money.cost > aveCost) {
                highCosts.add(money);
            } else if (money.cost < aveCost) {
                lowCosts.add(money);
            }
        }

        ArrayList<CostBill> costBills = payMeTheMoney(highCosts, lowCosts, aveCost);

        System.out.println(billGroup.title);
        for (CostBill costBill : costBills) {
            System.out.println(costBill.from + " 应付给 " + costBill.to + " " + costBill.cost);

            View view = LayoutInflater.from(getContext()).inflate(R.layout.piece_pay, null, false);
            ImageView avatarPay = (ImageView) view.findViewById(R.id.iv_avatar_pay);
            ImageView avatarPaid = (ImageView) view.findViewById(R.id.iv_avatar_paid);
            TextView userPay = (TextView) view.findViewById(R.id.tv_name_pay);
            TextView userPaid = (TextView) view.findViewById(R.id.tv_name_paid);
            TextView cost = (TextView) view.findViewById(R.id.tv_cost);
            userPay.setText(costBill.from);
            userPaid.setText(costBill.to);
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            cost.setText(df.format(costBill.cost));
            if (costBill.from.equals("何卫兵")) {
                avatarPay.setImageResource(R.drawable.ic_avatar_bing);
            } else if (costBill.from.equals("王正昕")) {
                avatarPay.setImageResource(R.drawable.ic_avatar_xin);
            } else {
                avatarPay.setImageResource(R.drawable.ic_avatar_feng);
            }

            if (costBill.to.equals("何卫兵")) {
                avatarPaid.setImageResource(R.drawable.ic_avatar_bing);
            } else if (costBill.to.equals("王正昕")) {
                avatarPaid.setImageResource(R.drawable.ic_avatar_xin);
            } else {
                avatarPaid.setImageResource(R.drawable.ic_avatar_feng);
            }
            this.addView(view);
        }

    }

    private ArrayList<CostBill> payMeTheMoney(ArrayList<Money> highCosts, ArrayList<Money> lowCosts, double aveCost) {
        ArrayList<CostBill> costBills = new ArrayList<>();
        for (Money money : highCosts) {
            double shouldGet = money.cost - aveCost;
            double total = 0;
            double lastTotal = 0;
            for (Money mon : lowCosts) {
                if (mon.cost == aveCost) {
                    continue;
                }
                CostBill costBill = new CostBill();
                total += (aveCost - mon.cost);
                costBill.from = mon.name;
                costBill.to = money.name;
                if (total >= shouldGet) {
                    costBill.cost = shouldGet - lastTotal;
                    mon.cost += costBill.cost;
                    costBills.add(costBill);
                    break;
                } else {
                    costBill.cost = aveCost - mon.cost;
                    mon.cost += costBill.cost;
                    costBills.add(costBill);
                    lastTotal = total;
                }

            }
        }

        return costBills;
    }

    class CostBill {
        String from, to;
        double cost;
    }

    class Money {
        String name;
        double cost;
    }
}
