package ch.ost.group20.speedcamerareminder.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        holder.tvPlace.setText(speedCameraList.get(position).getPlaceCombined());
        holder.tvStreet.setText(speedCameraList.get(position).getStreet());
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,  view.getContext().getResources().getString(R.string.speedcamera_share_msg) + "\n"
                        + speedCameraList.get(position).getPlaceCombined() + "\n"
                        + speedCameraList.get(position).getStreet());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                view.getContext().startActivity(shareIntent);

            }
        });

        holder.tvStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setTitle(speedCameraList.get(position).getStreet() + ", " + speedCameraList.get(position).getPlace())
                        .setMessage(view.getContext().getResources().getString(R.string.speedcamera_google_maps_msg))
                        .setPositiveButton(view.getContext().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + speedCameraList.get(position).getStreet() + ", " + speedCameraList.get(position).getPlace());
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                if (mapIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                                    view.getContext().startActivity(mapIntent);
                                }
                            }
                        })
                        .setNegativeButton(view.getContext().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.speedCameraList.size();
    }
}
