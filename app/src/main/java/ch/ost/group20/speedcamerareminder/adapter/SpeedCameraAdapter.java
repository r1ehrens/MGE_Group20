package ch.ost.group20.speedcamerareminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ch.ost.group20.speedcamerareminder.R;
import ch.ost.group20.speedcamerareminder.entity.SpeedCamera;

public class SpeedCameraAdapter extends RecyclerView.Adapter<SpeedCameraViewHolder> {

    List<SpeedCamera> speedCameraList;

    public SpeedCameraAdapter(List<SpeedCamera> speedCameraList) {
        this.speedCameraList = speedCameraList;
    }

    @Override
    public SpeedCameraViewHolder onCreateViewHolder(ViewGroup parent, int vt) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(
                R.layout.speedcamera_list_item,
                parent,
                false);

        TextView tvPlace = view.findViewById(R.id.tv_speedcamera_place);
        TextView tvStreet = view.findViewById(R.id.tv_speedcamera_street);
        ImageView ivShare = view.findViewById(R.id.iv_speedcamera_share);

        return new SpeedCameraViewHolder(
                view,
                tvPlace,
                tvStreet,
                ivShare);
    }

    @Override
    public void onBindViewHolder(SpeedCameraViewHolder holder, final int position) {
        holder.tvPlace.setText(speedCameraList.get(position).getPlace());
        holder.tvStreet.setText(speedCameraList.get(position).getStreet());
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "tammi siech share: " + position, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        return this.speedCameraList.size();
    }
}
