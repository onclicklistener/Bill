package com.davesla.bill.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.davesla.bill.R;
import com.davesla.bill.ui.activity.BaseActivity;
import com.davesla.bill.util.DensityUtil;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by hwb on 15/12/8.
 */
public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {

    private BaseActivity context;

    public BillAdapter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public BillAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillAdapter.ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mChart.getLayoutParams();
        params.height = DensityUtil.dip2px(context, 33 * 3);
        holder.mChart.setLayoutParams(params);
        setData(holder.mChart);
        holder.mChart.animateY(1500);
        holder.mChart.getLegend().setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        HorizontalBarChart mChart;

        public ViewHolder(View itemView) {
            super(itemView);
            mChart = (HorizontalBarChart) itemView.findViewById(R.id.chart);
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

            mChart.getAxisLeft().setDrawGridLines(false);
        }

    }

    private void setData(HorizontalBarChart mChart) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        barEntries.add(new BarEntry(200, 0));
        barEntries.add(new BarEntry(300, 1));
        barEntries.add(new BarEntry(400, 2));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("何卫兵");
        xVals.add("封光");
        xVals.add("王正昕");

        int colors[] = new int[]{Color.rgb(44, 133, 191), Color.rgb(122, 176, 88), Color.rgb(224, 72, 93)};
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
