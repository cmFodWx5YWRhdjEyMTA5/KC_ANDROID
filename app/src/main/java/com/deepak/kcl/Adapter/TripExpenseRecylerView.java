package com.deepak.kcl.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.R;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.Utils.TotalExpense;
import com.deepak.kcl.models.TripExpense;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TripExpenseRecylerView extends RecyclerView.Adapter<TripExpenseRecylerView.TripExpenseViewHolder> {

    private Context mContext;
    private List<TripExpense> mTripExpenseList;
    int pos;
    private String total_amount;

    public TripExpenseRecylerView(Context mContext, List<TripExpense> mTripExpenseList) {
        this.mContext = mContext;
        this.mTripExpenseList = mTripExpenseList;
    }

    @NonNull
    @Override
    public TripExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_tripclosure,parent,false);
        return new TripExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripExpenseViewHolder holder, int position) {
        TripExpense tripExpense = mTripExpenseList.get(position);

        Picasso.with(mContext)
                .load(Common.Trip_Expense_url+tripExpense.getTripexp_img())
                .placeholder(R.drawable.load)
                .into(holder.imgExp);

        holder.txtExpType.setText(tripExpense.getTripexp_type());
        holder.txtExpAmount.setText(tripExpense.getTripexp_amount());
        pos = mTripExpenseList.size();
        total_amount = mTripExpenseList.get(pos-1).getTotalExpense();

        if(position == 0){
            if(mContext instanceof TotalExpense) {
                ((TotalExpense) mContext).updateExpenseAmount(total_amount);
            }
        }
        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hello1", Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return mTripExpenseList.size();
    }

    public class TripExpenseViewHolder extends RecyclerView.ViewHolder {

        ImageView imgExp;
        TextView txtExpType,txtExpAmount;
        ImageButton imgBtnEdit;
        public TripExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExp = itemView.findViewById(R.id.trip_exp_imgview);
            txtExpType = itemView.findViewById(R.id.trip_exp_type);
            txtExpAmount = itemView.findViewById(R.id.trip_exp_amount);
            imgBtnEdit = itemView.findViewById(R.id.trip_exp_btnedit);
        }
    }
}
