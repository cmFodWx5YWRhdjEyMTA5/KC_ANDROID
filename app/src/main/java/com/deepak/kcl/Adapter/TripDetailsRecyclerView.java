package com.deepak.kcl.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Activities.AdvancesActivity;
import com.deepak.kcl.Activities.SplitAdvanActivity;
import com.deepak.kcl.Activities.StatusActivity;
import com.deepak.kcl.Activities.SummaryActivity;
import com.deepak.kcl.Activities.TripClosureActivity;
import com.deepak.kcl.Activities.TripDetailsActivity;
import com.deepak.kcl.Activities.TripReconActivity;
import com.deepak.kcl.R;
import com.deepak.kcl.models.TripDetails;
import com.deepak.kcl.models.TripList;

import java.util.List;

public class TripDetailsRecyclerView extends RecyclerView.Adapter<TripDetailsRecyclerView.TripDetailsViewHolder> {

    private Context mContext;
    private List<TripDetails> mTripDetails;

    public TripDetailsRecyclerView(Context mContext, List<TripDetails> mTripDetails) {
        this.mContext = mContext;
        this.mTripDetails = mTripDetails;
    }

    @NonNull
    @Override
    public TripDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_details,parent,false);
        return new TripDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripDetailsViewHolder holder, int i) {
            //holder.txtTripDetailId.setText(mTripDetails.get(i).getDetail_id());
            holder.txtTripDetailName.setText(mTripDetails.get(i).getDetail_name());
    }

    @Override
    public int getItemCount() {
        return mTripDetails.size();
    }

    public class TripDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView txtTripDetailId,txtTripDetailName;
        Intent intent;
        public TripDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            //txtTripDetailId = itemView.findViewById(R.id.txttrip_detail_id);
            txtTripDetailName = itemView.findViewById(R.id.txttrip_detail_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        TripDetails clickedDataItem = mTripDetails.get(pos);
                        if(clickedDataItem.getDetail_name().equals("Summary")){
                            intent = new Intent(mContext, SummaryActivity.class);
                        }
                        if(clickedDataItem.getDetail_name().equals("Advances")){
                            intent = new Intent(mContext, AdvancesActivity.class);
                        }
                        if(clickedDataItem.getDetail_name().equals("Status")){
                            intent = new Intent(mContext, StatusActivity.class);
                        }
                        if(clickedDataItem.getDetail_name().equals("Splitting Advances")){
                            intent = new Intent(mContext, SplitAdvanActivity.class);
                        }
                        if(clickedDataItem.getDetail_name().equals("Trip Closure")){
                            intent = new Intent(mContext, TripClosureActivity.class);
                        }
                        if(clickedDataItem.getDetail_name().equals("Trip Recon")){
                            intent = new Intent(mContext, TripReconActivity.class);
                        }
                        intent.putExtra("detailsname", mTripDetails.get(pos).getDetail_name());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        //Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getDetail_name(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
