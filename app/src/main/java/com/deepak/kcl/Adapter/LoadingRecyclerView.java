package com.deepak.kcl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.deepak.kcl.R;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.models.LoadingDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoadingRecyclerView extends RecyclerView.Adapter<LoadingRecyclerView.LoadingViewHolder> {

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
       LoadingDetails loadingDetails = mLoadingResponseList.get(position);

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

    }

    @Override
    public int getItemCount() {
        return mLoadingResponseList.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLoading;
        TextView txtLoadType,txtLoadQty;
        ImageButton imgBtnEdit;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            imgLoading = itemView.findViewById(R.id.trip_exp_imgview);
            txtLoadType = itemView.findViewById(R.id.trip_exp_type);
            txtLoadQty = itemView.findViewById(R.id.trip_exp_amount);
            imgBtnEdit = itemView.findViewById(R.id.trip_exp_btnedit);
        }
    }
}
