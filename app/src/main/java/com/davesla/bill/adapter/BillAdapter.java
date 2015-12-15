package com.davesla.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.davesla.bill.R;
import com.davesla.bill.service.bean.Bill;
import com.davesla.bill.service.bean.BillGroup;
import com.davesla.bill.ui.activity.BaseActivity;
import com.davesla.bill.ui.activity.DetailActivity;
import com.davesla.bill.ui.widget.BillWidget;
import com.davesla.bill.util.DensityUtil;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by hwb on 15/12/8.
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private SuperRecyclerView recyclerView;
    private BaseActivity context;
    private ArrayList<BillGroup> billGroups;

    public BillAdapter(SuperRecyclerView recyclerView,BaseActivity context, ArrayList<BillGroup> billGroups) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.billGroups = billGroups;
    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillAdapter.ViewHolder holder, int position) {
        BillGroup billGroup = billGroups.get(position);
        holder.title.setText(billGroup.title);
        Bill bill = billGroup.bills.get(billGroup.bills.size() - 1);
        if (bill.getUserName().equals("何卫兵")) {
            holder.avatar.setImageResource(R.drawable.ic_avatar_bing);
        } else if (bill.getUserName().equals("王正昕")) {
            holder.avatar.setImageResource(R.drawable.ic_avatar_xin);
        } else {
            holder.avatar.setImageResource(R.drawable.ic_avatar_feng);
        }
        holder.name.setText(bill.getUserName());
        holder.category.setText(bill.getCategoryName());
        holder.cost.setText(bill.getCost() + "");
        holder.date.setText(DateFormat.getDateInstance(DateFormat.DEFAULT).format(billGroup.start) + "-"
                + DateFormat.getDateInstance(DateFormat.DEFAULT).format(billGroup.end));

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mChart.getLayoutParams();
        params.height = DensityUtil.dip2px(context, 33 * billGroup.groupMembers.length);
        holder.mChart.setLayoutParams(params);
        setData(holder.mChart, billGroup);
        holder.mChart.animateY(500);
        holder.mChart.getLegend().setEnabled(false);

        holder.billWidget.setup(billGroup);

    }

    @Override
    public int getItemCount() {
        return billGroups.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, name, category, cost, date;
        ImageView avatar;
        HorizontalBarChart mChart;
        BillWidget billWidget;

        public ViewHolder(View itemView) {
            super(itemView);
            mChart = (HorizontalBarChart) itemView.findViewById(R.id.chart);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            category = (TextView) itemView.findViewById(R.id.tv_category);
            cost = (TextView) itemView.findViewById(R.id.tv_cost);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            billWidget = (BillWidget) itemView.findViewById(R.id.bill);

            mChart.setDescription("");
            mChart.setMaxVisibleValueCount(60);
            mChart.setPinchZoom(false);
            mChart.setScaleEnabled(false);
            mChart.setDrawBarShadow(false);
            mChart.setDrawGridBackground(false);
            mChart.setHighlightPerTapEnabled(false);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setSpaceBetweenLabels(0);
            xAxis.setDrawGridLines(false);

            mChart.getAxisLeft().setDrawAxisLine(false);
            mChart.getAxisRight().setDrawAxisLine(false);
            mChart.getAxisRight().setDrawLabels(false);

            mChart.getAxisLeft().setDrawGridLines(false);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = recyclerView.getRecyclerView().getChildAdapterPosition(v);
            DetailActivity.start(context,billGroups.get(position).bills);
        }
    }

    private void setData(HorizontalBarChart mChart, BillGroup billGroup) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < billGroup.users.size(); i++) {
            xVals.add(billGroup.users.get(i));
            barEntries.add(new BarEntry(billGroup.costs.get(i).intValue(), i));
        }

        int colors[] = new int[]{context.getResourceColor(R.color.colorAccent)};
        BarDataSet barDataSet = new BarDataSet(barEntries, "支出金额");
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet);

        BarData data = new BarData(xVals, dataSets);

        mChart.setData(data);
        mChart.invalidate();
    }


}
