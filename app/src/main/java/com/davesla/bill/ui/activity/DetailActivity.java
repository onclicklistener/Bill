package com.davesla.bill.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.davesla.bill.R;
import com.davesla.bill.adapter.DetailAdapter;
import com.davesla.bill.util.SystemInfoUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    private AppBarLayout appBarLayout;
    private LinearLayout layoutTitle;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private SuperRecyclerView recyclerView;

    private int statusBarHeight;
    private BarChart barChart;

    @Override
    protected void setContent() {
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void initView() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        barChart = (BarChart) findViewById(R.id.chart);
        layoutTitle = (LinearLayout) findViewById(R.id.layout_title);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (SuperRecyclerView) findViewById(R.id.list);
    }

    @Override
    protected void initData() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("第一期账单");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        colorStatusBar();

        initChart();
        setData();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (statusBarHeight == 0) {
                    statusBarHeight = SystemInfoUtil.getStatusBarHeight(DetailActivity.this);
                }

                float alpha = Math.abs(verticalOffset) / ((float) layoutTitle.getHeight() - statusBarHeight - toolbar.getHeight());
                toolbar.setAlpha(alpha);

                if (Math.abs(verticalOffset) >= (layoutTitle.getHeight() - (toolbar.getHeight() + statusBarHeight))) {
                    fab.hide();
                } else {
                    fab.show();
                }


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter( new DetailAdapter(this));
    }

    private void initChart() {
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);
        barChart.setHighlightPerTapEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setTextColor(getResourceColor(R.color.textGray));
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setTextColor(getResourceColor(R.color.textGray));
        barChart.getAxisRight().setTextColor(getResourceColor(R.color.textGray));
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawLabels(false);


        barChart.getAxisLeft().setDrawGridLines(false);

    }

    private void setData() {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

        barEntries.add(new BarEntry(50, 0));
        barEntries.add(new BarEntry(70, 1));
        barEntries.add(new BarEntry(300, 2));
        barEntries.add(new BarEntry(100, 3));
        barEntries.add(new BarEntry(600, 4));
        barEntries.add(new BarEntry(400, 5));
        barEntries.add(new BarEntry(200, 6));

        ArrayList<String> names = new ArrayList<String>();
        names.add("纯净水");
        names.add("生活用品");
        names.add("电费");
        names.add("水费");
        names.add("食品");
        names.add("燃气费");
        names.add("其它");

        int colors[] = new int[]{getResources().getColor(R.color.colorAccent)};
        BarDataSet barDataSet = new BarDataSet(barEntries, "各项支出");
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setColors(colors);
        barDataSet.setDrawValues(false);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(barDataSet);

        BarData data = new BarData(names, dataSets);

        barChart.setData(data);
        barChart.invalidate();

        barChart.animateY(1500);
        barChart.getLegend().setEnabled(false);
    }


//    private void setRightData() {
//        ArrayList<Entry> entries = new ArrayList<Entry>();
//
//        entries.add(new Entry(50.f, 0));
//        entries.add(new Entry(60.f, 0));
//        entries.add(new Entry(70.f, 0));
//        entries.add(new Entry(70.f, 0));
//        entries.add(new Entry(200.f, 0));
//        entries.add(new Entry(300.f, 0));
//
//        ArrayList<String> names = new ArrayList<String>();
//        names.add("纯净水");
//        names.add("水费");
//        names.add("电费");
//        names.add("买菜");
//        names.add("厨房用具");
//        names.add("其它");
//
//        PieDataSet dataSet = new PieDataSet(entries, "各项支出");
//        dataSet.setSliceSpace(2f);
//        dataSet.setSelectionShift(5f);
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(Color.rgb(44, 133, 191));
//        colors.add(Color.rgb(122, 176, 88));
//        colors.add(Color.rgb(224, 72, 93));
//        colors.add(Color.rgb(112, 72, 93));
//        colors.add(Color.rgb(224, 200, 93));
//        colors.add(Color.rgb(224, 72, 200));
//        dataSet.setColors(colors);
//
//        PieData data = new PieData(names, dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(10f);
//        data.setValueTextColor(Color.WHITE);
//        barChart.setData(data);
//        barChart.highlightValues(null);
//
//
//        barChart.invalidate();
//
//        barChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//        Legend l = barChart.getLegend();
//        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//    }

    public static void start(Context context) {
        Intent intent = new Intent(context, DetailActivity.class);
        context.startActivity(intent);
    }
}
