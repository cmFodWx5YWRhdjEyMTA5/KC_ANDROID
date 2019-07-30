package com.deepak.kcl.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.SplitAdvData;
import com.deepak.kcl.models.SplitAdvDataResponse;
import com.deepak.kcl.models.User;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplitAdvanceAdapter extends ExpandableRecyclerViewAdapter<SplitAdvanceAdapter.SplitAdvHeadViewHolder, SplitAdvanceAdapter.SplitAdvItemViewHolder> {

    private Context mContext;
    DatePickerDialog datePickerDialog;
    SplitAdvData splitAdvData;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    User user;
    BranchTrips branchTrips;

    public SplitAdvanceAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        mContext = context;
    }

    @Override
    public SplitAdvHeadViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_spliting_advance,parent,false);
        return new SplitAdvHeadViewHolder(view);
    }

    @Override
    public SplitAdvItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spliting_advance,parent,false);
        return new SplitAdvItemViewHolder(view1);
    }

    @Override
    public void onBindChildViewHolder(SplitAdvItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        splitAdvData = (SplitAdvData) group.getItems().get(childIndex);

        holder.setSplitDate(splitAdvData.getSplit_date());
        holder.setSplitType(splitAdvData.getSplit_type());
        holder.setSplitAmount(splitAdvData.getAmount());
        holder.setSplitBranch(splitAdvData.getBranch_name());
        holder.setSplitDesc(splitAdvData.getDescription());

        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDeleteDialog(childIndex,splitAdvData.getSplit_advId());
            }
        });
    }

    @Override
    public void onBindGroupViewHolder(SplitAdvHeadViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setHeadName(group.getTitle());
        holder.imgBtnSplitDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstWord = group.getTitle();
                if(firstWord.contains(" ")){
                    firstWord= firstWord.substring(0, firstWord.indexOf(" "));
                }
                OpenAddExpenseDialog(firstWord);
            }
        });
    }

    public class SplitAdvItemViewHolder extends ChildViewHolder {

        private TextView txtDate,txtSplit,txtAmount,txtBranch,txtDesc;
        private ImageButton imgBtnDelete;

        public SplitAdvItemViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.item_split_txtDate);
            txtSplit = itemView.findViewById(R.id.item_split_txtType);
            txtAmount = itemView.findViewById(R.id.item_split_txtAmt);
            txtBranch = itemView.findViewById(R.id.item_split_txtBranch);
            txtDesc = itemView.findViewById(R.id.item_split_txtDesc);
            imgBtnDelete = itemView.findViewById(R.id.item_split_btnDelete);
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

    public class SplitAdvHeadViewHolder extends GroupViewHolder {

        private TextView txtHeadSplitAdv,txtHeadSplitAmt;
        ImageButton imgBtnSplitDelete;
        public SplitAdvHeadViewHolder(View itemView) {
            super(itemView);

            txtHeadSplitAdv = (TextView) itemView.findViewById(R.id.heading_txt_advType);
            txtHeadSplitAmt = (TextView) itemView.findViewById(R.id.heading_txt_splitAdvAmt);
            imgBtnSplitDelete = itemView.findViewById(R.id.imgbtn_splitting_delete);
        }

        public void setHeadName(String name)
        {
            txtHeadSplitAdv.setText(name);
        }

        public void setAmount(String amount)
        {
            txtHeadSplitAmt.setText(amount);
        }
    }

    private void OpenDeleteDialog(int pos,String splitId) {
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

        user = SharedPrefManager.getInstance(mContext).getUser();

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
                //removeItem(pos);
                Call<SplitAdvDataResponse> call = RetrofitClient.getInstance().getApi().DeleteSplitAdvance(Integer.parseInt(splitId),String.valueOf(user.getUser_id()));
                call.enqueue(new Callback<SplitAdvDataResponse>() {
                    @Override
                    public void onResponse(Call<SplitAdvDataResponse> call, Response<SplitAdvDataResponse> response) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<SplitAdvDataResponse> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public void OpenAddExpenseDialog(String splitHead) {
        Button btnSave, btnCancel;
        ImageButton imgBtnClose, imgBtnDate;
        Spinner spnSplit;
        EditText edtBranch;
        EditText edtDate, edtAmount, edtDesc;

        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.split_add_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.add_split_imgBtnClose);
        btnSave = dialog.findViewById(R.id.add_split_btnSave);
        btnCancel = dialog.findViewById(R.id.add_split_btnCancel);
        edtBranch = dialog.findViewById(R.id.add_split_edtBranch);
        spnSplit = dialog.findViewById(R.id.add_split_spnType);
        imgBtnDate = dialog.findViewById(R.id.add_split_imgBtnDate);
        edtDate = dialog.findViewById(R.id.add_split_edtDate);
        edtAmount = dialog.findViewById(R.id.add_split_edtAmount);
        edtDesc = dialog.findViewById(R.id.add_split_edtDesc);
        user = SharedPrefManager.getInstance(mContext).getUser();
        branchTrips = SharedPrefManager.getInstance(mContext).getBranchTrips();

        edtDate.setKeyListener(null);

        imgBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edtBranch.setText(user.getBranch_name());
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                mContext, R.array.SplitType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSplit.setAdapter(adapter1);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dt = edtDate.getText().toString();
                String splitType = spnSplit.getSelectedItem().toString();
                String amt = edtAmount.getText().toString().trim();
                String desc = edtDesc.getText().toString().trim();
                Call<SplitAdvDataResponse> call = RetrofitClient.getInstance().getApi().insertSplitAdvance(branchTrips.getLR(),dt,String.valueOf(user.getBranch_id()),splitType,amt,desc,splitHead,user.getUser_id());
                call.enqueue(new Callback<SplitAdvDataResponse>() {
                    @Override
                    public void onResponse(Call<SplitAdvDataResponse> call, Response<SplitAdvDataResponse> response) {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<SplitAdvDataResponse> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.cancel();
            }
        });

        dialog.show();
    }
}