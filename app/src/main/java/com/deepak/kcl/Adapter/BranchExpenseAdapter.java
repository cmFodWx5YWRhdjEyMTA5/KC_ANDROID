package com.deepak.kcl.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.deepak.kcl.R;
import com.deepak.kcl.models.BranchExpense;

import java.util.List;

public class BranchExpenseAdapter extends RecyclerView.Adapter<BranchExpenseAdapter.BranchExpenseViewHolder> {

    private Context mContext;
    private List<BranchExpense> mBranchExpenseList;

    public BranchExpenseAdapter(Context mContext, List<BranchExpense> mBranchExpenseList) {
        this.mContext = mContext;
        this.mBranchExpenseList = mBranchExpenseList;
    }

    @NonNull
    @Override
    public BranchExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_branch_expense,parent,false);
        return new BranchExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchExpenseViewHolder holder, int position) {
        BranchExpense branchExpense = mBranchExpenseList.get(position);

        holder.txtDate.setText(branchExpense.getBch_date());
        holder.txtBranch.setText(branchExpense.getBranch_name());
        holder.txtExpType.setText(branchExpense.getExpense_type());
        holder.txtLrNumber.setText(branchExpense.getLr_number());
        holder.txtTripExpType.setText(branchExpense.getTrip_expense_type());
        holder.txtAmount.setText("Rs."+branchExpense.getBch_amount()+"/-");

        if(holder.txtLrNumber.getText().toString().equals("")){
            holder.textLrNo.setVisibility(View.GONE);
            holder.txtLrNumber.setVisibility(View.GONE);
            holder.textTripExpType.setVisibility(View.GONE);
            holder.txtTripExpType.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mBranchExpenseList.size();
    }

    public class BranchExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView textLrNo,textTripExpType;
        TextView txtDate,txtBranch,txtExpType,txtLrNumber,txtTripExpType,txtAmount;
        ImageButton btnEdit,btnDelete;
        public BranchExpenseViewHolder(@NonNull View itemView) {
            super(itemView);

            textLrNo = itemView.findViewById(R.id.item_brachExpense_txtLrNo);
            textTripExpType = itemView.findViewById(R.id.item_brachExpense_txtTripExpType);
            txtDate = itemView.findViewById(R.id.item_brachExpense_date);
            txtBranch = itemView.findViewById(R.id.item_brachExpense_branchName);
            txtExpType = itemView.findViewById(R.id.item_brachExpense_ExpenseType);
            txtLrNumber = itemView.findViewById(R.id.item_brachExpense_LrNo);
            txtTripExpType = itemView.findViewById(R.id.item_brachExpense_TripExpType);
            txtAmount = itemView.findViewById(R.id.item_brachExpense_amount);
            btnEdit = itemView.findViewById(R.id.item_brachExpense_btnEdit);
            btnDelete = itemView.findViewById(R.id.item_brachExpense_btnDelete);

        }
    }
}
