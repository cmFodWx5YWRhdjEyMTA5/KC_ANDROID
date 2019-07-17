package com.deepak.kcl.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.models.BranchExpense;
import com.deepak.kcl.models.BranchExpenseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchExpenseAdapter extends RecyclerView.Adapter<BranchExpenseAdapter.BranchExpenseViewHolder> {

    BranchExpense branchExpense;
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
        branchExpense = mBranchExpenseList.get(position);

        holder.txtDate.setText(branchExpense.getBch_date());
        holder.txtBranch.setText(branchExpense.getBranch_id());
        holder.txtExpType.setText(branchExpense.getExpense_id());
        holder.txtLrNumber.setText(branchExpense.getLr_number());
        holder.txtTripExpType.setText(branchExpense.getTrip_expense_type());
        holder.txtAmount.setText("Rs."+branchExpense.getBch_amount()+"/-");

        if(holder.txtLrNumber.getText().toString().equals("")){
            holder.textLrNo.setVisibility(View.GONE);
            holder.txtLrNumber.setVisibility(View.GONE);
            holder.textTripExpType.setVisibility(View.GONE);
            holder.txtTripExpType.setVisibility(View.GONE);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDeleteDialog(position);
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void removeItem(int position) {
        mBranchExpenseList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mBranchExpenseList.size());
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
            btnEdit = itemView.findViewById(R.id.item_brachExpense_btnView);
            btnDelete = itemView.findViewById(R.id.item_brachExpense_btnDelete);

        }
    }

    private void OpenDeleteDialog(int pos) {
        Button btnDelete,btncancel;

        final Dialog dialog=new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        btnDelete = dialog.findViewById(R.id.dialog_delete_btndelete);
        btncancel = dialog.findViewById(R.id.dialog_delete_btnCancel);


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(pos);
                Call<BranchExpenseResponse> call = RetrofitClient.getInstance().getApi().DeleteBranchExp(Integer.parseInt(branchExpense.getBranch_expense_id()),String.valueOf(branchExpense.getUser_id()));
                call.enqueue(new Callback<BranchExpenseResponse>() {
                    @Override
                    public void onResponse(Call<BranchExpenseResponse> call, Response<BranchExpenseResponse> response) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<BranchExpenseResponse> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.cancel();
            }
        });

        dialog.show();
    }
}
