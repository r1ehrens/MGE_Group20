package ch.ost.group20.speedcamerareminder;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class SpeedingFineCalculator extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speeding_fine_calculator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());


        Spinner roadTypeSpinner = (Spinner)findViewById(R.id.road_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roadTypeArray, android.R.layout.simple_spinner_item);

        Spinner tempoLimitSpinner = (Spinner)findViewById(R.id.tempo_limit_spinner);
        ArrayAdapter<CharSequence> tempoAdapter = ArrayAdapter.createFromResource(this, R.array.tempoLimits, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadTypeSpinner.setAdapter(adapter);
        tempoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempoLimitSpinner.setAdapter(tempoAdapter);
    }
}
