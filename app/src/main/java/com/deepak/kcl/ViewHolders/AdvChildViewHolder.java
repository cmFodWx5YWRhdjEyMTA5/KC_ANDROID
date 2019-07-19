package com.deepak.kcl.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deepak.kcl.R;
import com.deepak.kcl.models.TripAdvance;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

@Layout(R.layout.item_expan_advance)
public class AdvChildViewHolder
{
    private static String TAG ="AdvChildViewHolder";

    @com.mindorks.placeholderview.annotations.View(R.id.txt_adv_date)
    TextView txtDate;

    @com.mindorks.placeholderview.annotations.View(R.id.txt_adv_amt)
    TextView txtAmount;

    @com.mindorks.placeholderview.annotations.View(R.id.txt_adv_branch)
    TextView txtBranch;

    @com.mindorks.placeholderview.annotations.View(R.id.txt_adv_desc)
    TextView txtDesc;

    private Context mContext;
    private TripAdvance mTripAdvance;

    public AdvChildViewHolder(Context mContext, TripAdvance mTripAdvance) {
        this.mContext = mContext;
        this.mTripAdvance = mTripAdvance;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        txtDate.setText(mTripAdvance.getDate());
        txtAmount.setText(mTripAdvance.getAmount());
        txtBranch.setText(mTripAdvance.getBranch());
        txtDesc.setText(mTripAdvance.getDesc());
    }
}