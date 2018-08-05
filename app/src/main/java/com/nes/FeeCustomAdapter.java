package com.nes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

class FeeCustomAdapter extends RecyclerView.Adapter<FeeCustomAdapter.ViewHolder>{

    private Context context;
    private ArrayList<FeesBase> feeDispList;

    public FeeCustomAdapter(Context context, ArrayList<FeesBase> feeDispList) {
        this.context=context;
        this.feeDispList=feeDispList;
    }

    @NonNull
    @Override
    public FeeCustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedetailscardview,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeeCustomAdapter.ViewHolder viewHolder, int position) {
        viewHolder.fees_name.setText(feeDispList.get(position).getFees_name());
        viewHolder.fees_total.setText(feeDispList.get(position).getTotal_fees());
        viewHolder.due_fees.setText(feeDispList.get(position).getDue_fees());
        viewHolder.status.setText(feeDispList.get(position).getStatus());
        if (feeDispList.get(position).getStatus().equals("Paid"))
            viewHolder.statusImg.setImageResource(R.drawable.icons8_ok_48);
        else
            viewHolder.statusImg.setImageResource(R.drawable.icons8_box_important_64);
    }

    @Override
    public int getItemCount() {
        return feeDispList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView fees_name,fees_total,due_fees,status;
        ImageView statusImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fees_name = (TextView)itemView.findViewById(R.id.fees_name);
            fees_total = (TextView)itemView.findViewById(R.id.fees_total);
            due_fees = (TextView)itemView.findViewById(R.id.due_fees);
            status = (TextView)itemView.findViewById(R.id.status);
            statusImg = (ImageView)itemView.findViewById(R.id.statusImg);
        }
    }
}
