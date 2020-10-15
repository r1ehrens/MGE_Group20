package ch.ost.group20.speedcamerareminder.adapter;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SpeedCameraViewHolder extends RecyclerView.ViewHolder {
    public TextView tvPlace;
    public TextView tvStreet;

    public SpeedCameraViewHolder(View parent, TextView tvPlace, TextView tvStreet) {
        super(parent);
        this.tvPlace = tvPlace;
        this.tvStreet = tvStreet;
    }
}
