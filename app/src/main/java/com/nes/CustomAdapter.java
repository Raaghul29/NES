package com.nes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<StudentData> sData;

    public CustomAdapter(Context context, List<StudentData> sData) {
        this.context = context;
        this.sData = sData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.std_Name.setText(sData.get(position).getStuname());
        viewHolder.GRN_no.setText(sData.get(position).getStugrn());
        viewHolder.ClassId.setText(sData.get(position).getClassname());
        if (sData.get(position).getStd_gen() != null){
            if (sData.get(position).getStd_gen().equals("M")) {
                viewHolder.std_image.setImageResource(R.drawable.male_student);
            } else {
                viewHolder.std_image.setImageResource(R.drawable.female_student);
            }
         }


        viewHolder.view_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OnClick","My Profile clicked");
                Toast.makeText(context,"You will see "+sData.get(position).getStuname()+" profile here",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,StudentHomeScreen.class);
                i.putExtra("studentName",sData.get(position).getStuname());
                i.putExtra("std_classname",sData.get(position).getClassname());
                i.putExtra("fatherName",sData.get(position).getFathername());
                i.putExtra("stdGrn",sData.get(position).getStugrn());
                i.putExtra("fees",sData.get(position).getFees());
                i.putExtra("stuid",sData.get(position).getStuid());
                for (int k=0;k<sData.get(position).getDuefee().length;k++)
                    System.out.println("Due Fee while setting : "+sData.get(position).getDuefee().toString());
                i.putExtra("Duefee",sData.get(position).getDuefee());
                i.putExtra("Duefees",sData.get(position).getDuefees());
                i.putExtra("paidStatus",sData.get(position).getPaidstatus());
                i.putExtra("allfee",sData.get(position).getAllfee());
                i.putExtra("download",sData.get(position).getDownload());
                i.putExtra("dadEmail",sData.get(position).getDadEmail());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView std_Name,GRN_no,ClassId,total_fees,fees_due;
        public LinearLayout view_profile;
        public LinearLayout std_receipt;
        public CircularImageView std_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            std_Name = (TextView)itemView.findViewById(R.id.Std_Name);
            GRN_no = (TextView)itemView.findViewById(R.id.GRN_no);
            view_profile = (LinearLayout)itemView.findViewById(R.id.view_profile);
            ClassId = (TextView)itemView.findViewById(R.id.ClassId);
            std_image = (CircularImageView)itemView.findViewById(R.id.std_image);

        }
    }
}
