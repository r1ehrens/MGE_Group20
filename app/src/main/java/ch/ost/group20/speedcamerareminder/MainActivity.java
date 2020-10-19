package ch.ost.group20.speedcamerareminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import ch.ost.group20.speedcamerareminder.adapter.SpeedCameraAdapter;
import ch.ost.group20.speedcamerareminder.entity.SpeedCamera;
import ch.ost.group20.speedcamerareminder.network.APIClient;
import ch.ost.group20.speedcamerareminder.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvCameraOverview;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView ivEmptyList;
    TextView tvEmptyListMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preference auslesen
        /*PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchNotification = sharedPref.getBoolean(SettingsActivity.KEY_NOTIFICATION_SWITCH, false);

        System.out.println(switchNotification);*/


        ivEmptyList = findViewById(R.id.iv_empty_list);
        tvEmptyListMsg = findViewById(R.id.tv_empty_list_msg);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_camera_overview);
        swipeRefreshLayout.setRefreshing(true);

        rvCameraOverview = findViewById(R.id.rv_camera_overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        Intent intentCalculator = new Intent(this, SpeedingFineCalculator.class);

        FloatingActionButton fabCalculator = (FloatingActionButton) findViewById(R.id.fabCalculator);
        fabCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentCalculator);
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataAndSetupList();
            }
        });


        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        rvCameraOverview.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCameraOverview.getContext(), layoutManager.getOrientation());
        rvCameraOverview.addItemDecoration(dividerItemDecoration);

        getDataAndSetupList();
    }

    private void getDataAndSetupList(){
        APIInterface apiInterface = null;
        if (apiInterface == null){
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }
        apiInterface.getSpeedCameras().enqueue(new Callback<List<SpeedCamera>>() {
            @Override
            public void onResponse(Call<List<SpeedCamera>> call, Response<List<SpeedCamera>> response) {

                if (!response.body().isEmpty()){
                    rvCameraOverview.setVisibility(View.VISIBLE);
                    ivEmptyList.setVisibility(View.GONE);
                    tvEmptyListMsg.setVisibility(View.GONE);

                    SpeedCameraAdapter speedCameraAdapter = new SpeedCameraAdapter(response.body());
                    rvCameraOverview.setAdapter(speedCameraAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    onFailure(call, new Throwable("Empty List"));
                }
            }

            @Override
            public void onFailure(Call<List<SpeedCamera>> call, Throwable t) {

                rvCameraOverview.setVisibility(View.GONE);
                ivEmptyList.setVisibility(View.VISIBLE);
                tvEmptyListMsg.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}