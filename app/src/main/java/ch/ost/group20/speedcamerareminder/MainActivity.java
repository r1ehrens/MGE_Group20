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



        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        apiInterface.getSpeedCameras().enqueue(new Callback<List<SpeedCamera>>() {
            @Override
            public void onResponse(Call<List<SpeedCamera>> call, Response<List<SpeedCamera>> response) {
                if (!response.body().isEmpty()){
                    SpeedCameraAdapter speedCameraAdapter = new SpeedCameraAdapter(response.body());
                    rvCameraOverview.setAdapter(speedCameraAdapter);

                } else {
                    onFailure(call, new Throwable("Empty List"));
                }
            }

            @Override
            public void onFailure(Call<List<SpeedCamera>> call, Throwable t) {
                ImageView ivEmptyList = findViewById(R.id.iv_empty_list);
                TextView tvEmptyListMsg = findViewById(R.id.tv_empty_list_msg);

                rvCameraOverview.setVisibility(View.GONE);
                ivEmptyList.setVisibility(View.VISIBLE);
                tvEmptyListMsg.setVisibility(View.VISIBLE);
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

            Toast.makeText(getApplicationContext(), "GO TO SETTINGS!", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}