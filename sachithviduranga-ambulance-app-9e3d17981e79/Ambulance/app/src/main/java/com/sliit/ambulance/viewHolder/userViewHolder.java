package com.sliit.ambulance.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.sliit.ambulance.R;

import com.sliit.ambulance.Interface.itemClickListner;

public class userViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView location1,location2;
    public itemClickListner listner;

    public userViewHolder(View itemView) {
        super(itemView);

        location1=itemView.findViewById(R.id.textViewLocation);
        location2=itemView.findViewById(R.id.textViewHospital);

    }

    public void setItemClickListner(itemClickListner listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);

    }
}
