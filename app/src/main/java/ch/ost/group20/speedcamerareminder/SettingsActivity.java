package ch.ost.group20.speedcamerareminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import ch.ost.group20.speedcamerareminder.notification.NotificationReceiver;
import ch.ost.group20.speedcamerareminder.timepicker.TimePreference;
import ch.ost.group20.speedcamerareminder.timepicker.TimePreferenceDialogFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_NOTIFICATION_SWITCH = "notification_switch_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        SwitchPreferenceCompat notificationEnabledSwitch;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            notificationEnabledSwitch = findPreference(getResources().getString(R.string.notification_enabled_key));
        }

        @Override
        public void onDisplayPreferenceDialog(Preference preference) {
            DialogFragment dialogFragment = null;
            if (preference instanceof TimePreference) {
                dialogFragment = new TimePreferenceDialogFragmentCompat();
                Bundle bundle = new Bundle(1);
                bundle.putString("key", preference.getKey());
                dialogFragment.setArguments(bundle);
            }

            if (dialogFragment != null) {
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference.PreferenceFragment.DIALOG");
            } else {
                super.onDisplayPreferenceDialog(preference);
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

            if (notificationEnabledSwitch.isChecked()) {

                //check if app is disabled from battery optimization
                PowerManager powerManager = (PowerManager) getActivity().getSystemService(POWER_SERVICE);
                if (!powerManager.isIgnoringBatteryOptimizations(getActivity().getPackageName())) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    i.setData(Uri.parse("package:" + getActivity().getPackageName()));
                    startActivityForResult(i, 1);
                }

                Intent intent = new Intent(getContext(), NotificationReceiver.class);
                intent.putExtra(getResources().getString(R.string.notification_intent_is_config), true);
                getContext().sendBroadcast(intent);
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == 1) {

                PowerManager powerManager = (PowerManager) getActivity().getSystemService(POWER_SERVICE);

                if (!powerManager.isIgnoringBatteryOptimizations(getActivity().getPackageName())) {
                    notificationEnabledSwitch.setChecked(false);
                }
            }
        }


        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}