package com.davesla.bill.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.davesla.bill.R;
import com.davesla.bill.ui.activity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by hwb on 15/12/8.
 */
public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private ArrayList<Integer> colors = new ArrayList<>();

    private BaseActivity context;

    public DetailAdapter(BaseActivity context) {
        this.context = context;
        colors.add(context.getResourceColor(R.color.colorElc));
        colors.add(context.getResourceColor(R.color.colorFood));
        colors.add(context.getResourceColor(R.color.colorGas));
        colors.add(context.getResourceColor(R.color.colorIssue));
        colors.add(context.getResourceColor(R.color.colorOthers));
        colors.add(context.getResourceColor(R.color.colorWater));
        colors.add(context.getResourceColor(R.color.colorPureWater));
    }

    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        holder.layoutRoot.setBackgroundColor(colors.get(position % colors.size()));

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layoutRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_root);

        }

    }


}
