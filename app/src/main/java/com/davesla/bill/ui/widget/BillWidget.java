package com.davesla.bill.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

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
        for (double cost : costs) {
            totalCost += cost;
        }
        double aveCost = totalCost / billGroup.groupMembers.length;

        ArrayList<CostBill> costBills = new ArrayList<>();
        for (int i = 0; i < costs.size(); i++) {
            double cost = costs.get(i);
            if (cost < aveCost) {
                for (int j = 0; j < costs.size(); j++) {
                    double jCost = costs.get(j);
                    if (jCost > aveCost) {
                        CostBill costBill = new CostBill();
                        costBill.from = users.get(i);
                        costBill.to = users.get(j);
                        costBill.cost = jCost - aveCost;
                        costBills.add(costBill);
                    }
                }

            }
        }

        for (CostBill costBill : costBills) {
            System.out.println(costBill.from + " 应付给 " + costBill.to + " " + costBill.cost);
        }

    }

    class CostBill {
        String from, to;
        double cost;
    }
}
