package ch.ost.group20.speedcamerareminder;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ch.ost.group20.speedcamerareminder.adapter.SpeedCameraAdapter;
import ch.ost.group20.speedcamerareminder.entity.SpeedCamera;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView rvCameraOverview = findViewById(R.id.rv_camera_overview);
        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        rvCameraOverview.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCameraOverview.getContext(), layoutManager.getOrientation());
        rvCameraOverview.addItemDecoration(dividerItemDecoration);

        List<SpeedCamera> speedCameraList = new ArrayList<>();
        speedCameraList.add(new SpeedCamera("Wangen0", "Rapperswil0", "Bliblauba0"));
        speedCameraList.add(new SpeedCamera("Wangen1", "Rapperswil1", "Bliblauba1"));
        speedCameraList.add(new SpeedCamera("Rapperswil2", "Rapperswil2", "Bliblauba2"));
        speedCameraList.add(new SpeedCamera("Wangen3", "Rapperswil3", "Bliblauba3"));
        speedCameraList.add(new SpeedCamera("Wangen4", "Rapperswil4", "Bliblauba4"));
        speedCameraList.add(new SpeedCamera("Wangen5", "Rapperswil5", "Bliblauba5"));
        speedCameraList.add(new SpeedCamera("Wangen6", "Rapperswil6", "Bliblauba6"));
        speedCameraList.add(new SpeedCamera("Wangen7", "Rapperswil7", "Bliblauba7"));
        speedCameraList.add(new SpeedCamera("Wangen8", "Rapperswil8", "Bliblauba8"));
        speedCameraList.add(new SpeedCamera("Wangen9", "Rapperswil9", "Bliblauba9"));

       // speedCameraList.clear();
        if (speedCameraList.isEmpty()){
            ImageView ivEmptyList = findViewById(R.id.iv_empty_list);
            TextView tvEmptyListMsg = findViewById(R.id.tv_empty_list_msg);

            rvCameraOverview.setVisibility(View.GONE);
            ivEmptyList.setVisibility(View.VISIBLE);
            tvEmptyListMsg.setVisibility(View.VISIBLE);
        } else {
            SpeedCameraAdapter speedCameraAdapter = new SpeedCameraAdapter(speedCameraList);
            rvCameraOverview.setAdapter(speedCameraAdapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Toast.makeText(getApplicationContext(), "GO TO SETTINGS!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}