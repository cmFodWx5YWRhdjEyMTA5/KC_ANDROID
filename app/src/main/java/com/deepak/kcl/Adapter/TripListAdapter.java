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
import com.deepak.kcl.Activities.TripDetailsActivity;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.BranchTripsResponse;
import com.deepak.kcl.models.TripList;
import java.util.List;


public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripListViewHolder> {

    private Context mContext;
    private List<BranchTrips> mTripList;

    public TripListAdapter(Context mContext, List<BranchTrips> mTripList) {
        this.mContext = mContext;
        this.mTripList = mTripList;
    }

    @NonNull
    @Override
    public TripListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_triplist,parent,false);
        return new TripListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripListViewHolder holder, int position) {

        holder.txtStartDate.setText(mTripList.get(position).getS_date());
        holder.txtEndDate.setText(mTripList.get(position).getE_date());
        holder.txtClientName.setText(mTripList.get(position).getClient_name());
        //holder.txtRouteNumber.setText(mTripList.get(position).get);
        holder.txtVehicleNumber.setText(mTripList.get(position).getVehicle_no());
        holder.txtLrNumber.setText(mTripList.get(position).getLR());
        holder.txtTripStatus.setText("OnGoing");
        //String status = mTripList.get(position).getTripStatus();
        String status = "OnGoing";

        if(status.equals("UpComing")) {
            //holder.txtTripStatus.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        if(status.equals("Completed")) {
            //holder.txtTripStatus.setTextColor(mContext.getResources().getColor(R.color.colorlogo));
        }
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    public class TripListViewHolder extends RecyclerView.ViewHolder {
        TextView txtStartDate,txtEndDate,txtClientName,txtRouteNumber,txtVehicleNumber,txtLrNumber,txtTripStatus;
        public TripListViewHolder(@NonNull View itemView) {
            super(itemView);

            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtClientName = itemView.findViewById(R.id.txtClientName);
            txtRouteNumber  = itemView.findViewById(R.id.txtRouteNumber);
            txtVehicleNumber  = itemView.findViewById(R.id.txtVehicleNumber);
            txtLrNumber  = itemView.findViewById(R.id.txtLrNumber);
            txtTripStatus = itemView.findViewById(R.id.txtTripStatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    SharedPrefManager.getInstance(mContext).clearTrip();
                    if (pos != RecyclerView.NO_POSITION){
                        BranchTrips clickedDataItem = mTripList.get(pos);
                        SharedPrefManager.getInstance(mContext).saveTrip(clickedDataItem);
                        Intent intent = new Intent(mContext, TripDetailsActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
