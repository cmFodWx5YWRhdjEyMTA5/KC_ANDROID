package com.deepak.kcl.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.deepak.kcl.models.SplitAdvHead;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;

@Layout(R.layout.item_spliting_advance)
public class SplitAdvItemViewHolder
{
    private static String TAG ="SplitAdvItemViewHolder";

    @com.mindorks.placeholderview.annotations.View(R.id.item_split_txtDate)
    TextView txtsplitDate;

    @com.mindorks.placeholderview.annotations.View(R.id.item_split_txtAmt)
    TextView txtsplitAmount;

    @com.mindorks.placeholderview.annotations.View(R.id.item_split_txtBranch)
    TextView txtsplitBranch;

    @com.mindorks.placeholderview.annotations.View(R.id.item_split_txtDesc)
    TextView txtsplitDesc;

    @com.mindorks.placeholderview.annotations.View(R.id.item_split_txtType)
    TextView txtsplitType;

    private Context mContext;
    private SplitAdvHead mSplitAdv;

    public SplitAdvItemViewHolder(Context mContext, SplitAdvHead mSplitAdv) {
        this.mContext = mContext;
        this.mSplitAdv = mSplitAdv;
    }

    @Resolve
    private void onResolve(){
        Log.d(TAG,"onResolve");
        txtsplitAmount.setText(mSplitAdv.getAmount());
    }
}
