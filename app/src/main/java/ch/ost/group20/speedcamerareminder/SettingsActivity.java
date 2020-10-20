package ch.ost.group20.speedcamerareminder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import ch.ost.group20.speedcamerareminder.timepicker.TimePreference;
import ch.ost.group20.speedcamerareminder.timepicker.TimePreferenceDialogFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
    public  static final String KEY_NOTIFICATION_SWITCH = "notification_switch_key";

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

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onDisplayPreferenceDialog(Preference preference) {
            DialogFragment dialogFragment = null;
            if (preference instanceof TimePreference)
            {
                dialogFragment = new TimePreferenceDialogFragmentCompat();
                Bundle bundle = new Bundle(1);
                bundle.putString("key", preference.getKey());
                dialogFragment.setArguments(bundle);
            }

            if (dialogFragment != null)
            {
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference.PreferenceFragment.DIALOG");
            }
            else
            {
                super.onDisplayPreferenceDialog(preference);
            }
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