package com.deepak.kcl.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.deepak.kcl.models.SplitAdvHead;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SplitAdvItemViewHolder extends ChildViewHolder {

    private TextView txtDate,txtSplit,txtAmount,txtBranch,txtDesc;

    public SplitAdvItemViewHolder(View itemView) {
        super(itemView);

        txtDate = itemView.findViewById(R.id.item_split_txtDate);
        txtSplit = itemView.findViewById(R.id.item_split_txtType);
        txtAmount = itemView.findViewById(R.id.item_split_txtAmt);
        txtBranch = itemView.findViewById(R.id.item_split_txtBranch);
        txtDesc = itemView.findViewById(R.id.item_split_txtDesc);
    }

    public void setSplitDate(String dt)
    {
        txtDate.setText(dt);
    }

    public void setSplitType(String type)
    {
        txtSplit.setText(type);
    }

    public void setSplitAmount(String amt)
    {
        txtAmount.setText(amt);
    }

    public void setSplitBranch(String branch)
    {
        txtBranch.setText(branch);
    }

    public void setSplitDesc(String desc)
    {
        txtDesc.setText(desc);
    }
}
/*@Layout(R.layout.item_spliting_advance)
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
}*/
