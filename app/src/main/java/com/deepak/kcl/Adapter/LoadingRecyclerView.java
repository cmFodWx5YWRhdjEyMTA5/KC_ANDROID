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

import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.LoadingDetails;
import com.deepak.kcl.models.LoadingResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingRecyclerView extends RecyclerView.Adapter<LoadingRecyclerView.LoadingViewHolder> {

    LoadingDetails loadingDetails;
    BranchTrips branchTrips;
    private Context mContext;
    private List<LoadingDetails> mLoadingResponseList;

    public LoadingRecyclerView(Context mContext, List<LoadingDetails> mLoadingResponseList) {
        this.mContext = mContext;
        this.mLoadingResponseList = mLoadingResponseList;
    }

    @NonNull
    @Override
    public LoadingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense_tripclosure,parent,false);
        return new LoadingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadingViewHolder holder, int position) {
       loadingDetails = mLoadingResponseList.get(position);

        final ImagePopup imagePopup = new ImagePopup(mContext);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(true);
        //imagePopup.setHideCloseIcon(true);  // Optional
        imagePopup.setImageOnClickClose(true);

        imagePopup.initiatePopupWithPicasso(Common.Loading_url+loadingDetails.getLoad_img());

        Picasso.with(mContext)
                .load(Common.Loading_url+loadingDetails.getLoad_img())
                .placeholder(R.drawable.load)
                .into(holder.imgLoading);

        holder.txtLoadType.setText(loadingDetails.getLoading_type());
        holder.txtLoadQty.setText(loadingDetails.getLoading_qty());

        holder.imgLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDeleteDialog(position);
            }
        });

    }

    private void removeItem(int position) {
        mLoadingResponseList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mLoadingResponseList.size());
    }

    @Override
    public int getItemCount() {
        return mLoadingResponseList.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLoading;
        TextView txtLoadType,txtLoadQty;
        ImageButton imgBtnDelete;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLoading = itemView.findViewById(R.id.trip_exp_imgview);
            txtLoadType = itemView.findViewById(R.id.trip_exp_type);
            txtLoadQty = itemView.findViewById(R.id.trip_exp_amount);
            imgBtnDelete = itemView.findViewById(R.id.trip_exp_btnDelete);
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

                Call<LoadingResponse> call = RetrofitClient.getInstance().getApi().DeleteLoadUnload(Integer.parseInt(loadingDetails.getLoad_id()),tripId);
                call.enqueue(new Callback<LoadingResponse>() {
                    @Override
                    public void onResponse(Call<LoadingResponse> call, Response<LoadingResponse> response) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoadingResponse> call, Throwable t) {
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
