package com.android.dnd;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dhyanesh on 1/26/16.
 */
public class SmsRecyclerViewAdapter extends RecyclerView.Adapter<SmsRecyclerViewAdapter.ViewHolder> {
    private ArrayList<SmsDetails> smsList;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        private Context mContext;
        public ViewHolder(View v, Context context) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.sms_text_item);
            mTextView.setOnClickListener(this);
            mContext = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("SmsRecyclerViewAdapter", "Click logged.");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setMessage("Clicked: " + v.toString());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public SmsRecyclerViewAdapter(ArrayList<SmsDetails> smsList) {
        this.smsList = smsList;
    }

    @Override
    public SmsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_text_layout, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SmsDetails sms = smsList.get(position);
        holder.mTextView.setText(sms.getDate().toString() + sms.getAddress() + sms.getBody());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return smsList.size();
    }
}
