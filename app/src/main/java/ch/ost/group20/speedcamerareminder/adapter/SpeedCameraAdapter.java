package ch.ost.group20.speedcamerareminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import ch.ost.group20.speedcamerareminder.R;
import ch.ost.group20.speedcamerareminder.entity.SpeedCamera;

public class SpeedCameraAdapter extends RecyclerView.Adapter<SpeedCameraViewHolder> {

    List<SpeedCamera> speedCameraList;

    public SpeedCameraAdapter(List<SpeedCamera> speedCameraList){
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

        TextView tvPlace = view.findViewById(R.id.tv_place);
        TextView tvStreet = view.findViewById(R.id.tv_street);

        return new SpeedCameraViewHolder(
                view,
                tvPlace,
                tvStreet);
    }

    @Override
    public void onBindViewHolder(SpeedCameraViewHolder holder, int position) {
        holder.tvPlace.setText(speedCameraList.get(position).getPlace());
        holder.tvStreet.setText(speedCameraList.get(position).getStreet());
    }

    @Override
    public int getItemCount() {
        return this.speedCameraList.size();
    }
}