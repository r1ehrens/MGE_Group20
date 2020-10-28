package ch.ost.group20.speedcamerareminder.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SpeedCameraViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPlace;
    public TextView tvStreet;
    public ImageView ivShare;

    public SpeedCameraViewHolder(View parent, TextView tvPlace, TextView tvStreet, ImageView ivShare) {
        super(parent);
        this.tvPlace = tvPlace;
        this.tvStreet = tvStreet;
        this.ivShare = ivShare;
    }
}
