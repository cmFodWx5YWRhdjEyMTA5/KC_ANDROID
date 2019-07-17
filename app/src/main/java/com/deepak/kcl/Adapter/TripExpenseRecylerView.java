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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.Utils.TotalExpense;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.TripExpense;
import com.deepak.kcl.models.TripExpenseResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripExpenseRecylerView extends RecyclerView.Adapter<TripExpenseRecylerView.TripExpenseViewHolder> {

    TripExpense tripExpense;
    BranchTrips branchTrips;
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
        tripExpense = mTripExpenseList.get(position);

        final ImagePopup imagePopup = new ImagePopup(mContext);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        //imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);

        imagePopup.initiatePopupWithPicasso(Common.Trip_Expense_url+tripExpense.getTripexp_img());

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
        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDeleteDialog(position,mTripExpenseList.get(position).getTripexp_id());
            }
        });

        holder.imgExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }

    private void removeItem(int position) {
        mTripExpenseList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mTripExpenseList.size());
    }

    @Override
    public int getItemCount() {
        return mTripExpenseList.size();
    }

    public class TripExpenseViewHolder extends RecyclerView.ViewHolder {

        ImageView imgExp;
        TextView txtExpType,txtExpAmount;
        ImageButton imgBtnDelete;
        public TripExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExp = itemView.findViewById(R.id.trip_exp_imgview);
            txtExpType = itemView.findViewById(R.id.trip_exp_type);
            txtExpAmount = itemView.findViewById(R.id.trip_exp_amount);
            imgBtnDelete = itemView.findViewById(R.id.trip_exp_btnDelete);
        }
    }

    private void OpenDeleteDialog(int pos,int tripExpId) {
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

        branchTrips = SharedPrefManager.getInstance(mContext).getBranchTrips();
        String tripId = String.valueOf(branchTrips.getTrip_id());

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

                Call<TripExpenseResponse> call = RetrofitClient.getInstance().getApi().DeleteTripExp(tripExpId,tripId);
                call.enqueue(new Callback<TripExpenseResponse>() {
                    @Override
                    public void onResponse(Call<TripExpenseResponse> call, Response<TripExpenseResponse> response) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TripExpenseResponse> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                removeItem(pos);
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
