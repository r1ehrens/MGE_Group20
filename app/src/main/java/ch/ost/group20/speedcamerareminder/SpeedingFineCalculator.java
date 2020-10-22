package ch.ost.group20.speedcamerareminder;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedingFineCalculator extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final static int MAXIMUM_SPEED_LIMIT = 500;
    private final static int SPEED_MARGE = 5;

    enum RoadTypes {INNERORTS, AUSSERORTS, AUTOBAHN}

    ;
    private int tempoLimitInput;
    private RoadTypes roadTypeInput;
    private int speedInput;

    private EditText tempoEditText;
    private Button calculateButton;
    private TextView fineOutput;
    private TextView additionalText;
    private TextView additionalInformation;
    private TextView fineCurrency;
    private TextView fineDescription;
    private Spinner tempoLimitSpinner;
    private Spinner roadTypeSpinner;

    private ArrayAdapter<CharSequence> tempoLimitAdapter;
    ArrayAdapter<CharSequence> roadTypeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speeding_fine_calculator);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fineOutput = findViewById(R.id.fine_output);
        additionalText = findViewById(R.id.addition_text);
        additionalInformation = findViewById(R.id.additional_information);
        fineCurrency = findViewById(R.id.fine_currency);
        fineDescription = findViewById(R.id.fine_description);

        tempoEditText = findViewById(R.id.tempo_effective_input);
        tempoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    return;
                }
                int value = Integer.parseInt(s.toString());
                if (value < 0 || value > MAXIMUM_SPEED_LIMIT) {
                    tempoEditText.setError(getString(R.string.speedErrorMessage));
                } else {
                    speedInput = value;
                }
            }
        });

        calculateButton = findViewById(R.id.calculate_button);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int speedDifference = speedInput - SPEED_MARGE - tempoLimitInput;
                fineOutput.setText("");
                additionalText.setText("");
                if (speedDifference < 0) {
                    hideResults();
                    additionalInformation.setVisibility(View.VISIBLE);
                    additionalInformation.setText("Es liegt keine Geschwindigkeitsüberschreitung vor.");
                } else {
                    int fine = getCalculatedFine(speedDifference);
                    fineOutput.setText(String.valueOf(fine));
                    displayResults();
                    setRacingInformation();
                }
            }
        });


        roadTypeSpinner = (Spinner) findViewById(R.id.road_type_spinner);
        roadTypeSpinner.setOnItemSelectedListener(this);
        roadTypeAdapter = ArrayAdapter.createFromResource(this, R.array.roadTypeArray, android.R.layout.simple_spinner_item);

        tempoLimitSpinner = (Spinner) findViewById(R.id.tempo_limit_spinner);
        tempoLimitSpinner.setOnItemSelectedListener(this);
        tempoLimitAdapter = ArrayAdapter.createFromResource(this, R.array.tempoLimits, android.R.layout.simple_spinner_item);

        roadTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roadTypeSpinner.setAdapter(roadTypeAdapter);
        tempoLimitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tempoLimitSpinner.setAdapter(tempoLimitAdapter);

        //Intent intentList = new Intent(this, MainActivity.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.road_type_spinner) {
            roadTypeInput = RoadTypes.valueOf(parent.getItemAtPosition(position).toString().toUpperCase());
            if (roadTypeInput == RoadTypes.INNERORTS) {
                tempoLimitSpinner.setSelection(tempoLimitAdapter.getPosition("50"));
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                tempoLimitSpinner.setSelection(tempoLimitAdapter.getPosition("80"));
            } else {
                tempoLimitSpinner.setSelection(tempoLimitAdapter.getPosition("120"));
            }
        } else if (parent.getId() == R.id.tempo_limit_spinner) {
            tempoLimitInput = Integer.parseInt(parent.getItemAtPosition(position).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int getCalculatedFine(int speedDifference) {
        if (speedDifference <= 5) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                return 40;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                return 40;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                return 20;
            }
        } else if (speedDifference <= 10) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                return 120;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                return 100;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                return 60;
            }
        } else if (speedDifference <= 15) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                return 160;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                return 120;
            }
        } else if (speedDifference <= 20) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                additionalText.setText(R.string.verzeigung);
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                return 240;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                return 180;
            }
        } else if (speedDifference <= 25) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                additionalText.setText(R.string.verzeigung);
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                additionalText.setText(R.string.verzeigung);
                return 240;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                return 260;
            }
        } else if (speedDifference < 40) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                additionalText.setText(R.string.verzeigung);
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                additionalText.setText(R.string.verzeigung);
                return 240;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                additionalText.setText(R.string.verzeigung);
                return 260;
            }
        } else if (speedDifference < 80) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                additionalText.setText(R.string.raserdelikt);
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                additionalText.setText(R.string.raserdelikt);
                return 240;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                additionalText.setText(R.string.verzeigung);
                return 260;
            }
        } else if (speedDifference >= 80) {
            if (roadTypeInput == RoadTypes.INNERORTS) {
                additionalText.setText(R.string.raserdelikt);
                return 250;
            } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
                additionalText.setText(R.string.raserdelikt);
                return 240;
            } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
                additionalText.setText(R.string.raserdelikt);
                return 260;
            }
        }
        return -1;
    }

    private void setRacingInformation() {
        if (additionalText.getText() != getString(R.string.raserdelikt)) {
            additionalInformation.setText("");
            return;
        }
        if (tempoLimitInput == 30) {
            additionalInformation.setText("*In der 30er-Zone gilt man ab einer Geschwindigkeit von 70 km/h als Raser!");
        } else if (roadTypeInput == RoadTypes.INNERORTS) {
            additionalInformation.setText("*In der 50er-Zone gilt man ab einer Geschwindigkeit von 100 km/h als Raser!");
        } else if (roadTypeInput == RoadTypes.AUSSERORTS) {
            additionalInformation.setText("*In der 80er-Zone gilt man ab einer Geschwindigkeit von 140 km/h als Raser!");
        } else if (roadTypeInput == RoadTypes.AUTOBAHN) {
            additionalInformation.setText("*In der 120er-Zone gilt man ab einer Geschwindigkeit von 200 km/h als Raser!");
        } else {
            additionalInformation.setText("");
        }
    }

    private void hideResults() {
        additionalText.setVisibility(View.INVISIBLE);
        fineOutput.setVisibility(View.INVISIBLE);
        additionalInformation.setVisibility(View.INVISIBLE);
        fineCurrency.setVisibility(View.INVISIBLE);
        fineDescription.setVisibility(View.INVISIBLE);
    }

    private void displayResults() {
        fineOutput.setVisibility(View.VISIBLE);
        fineCurrency.setVisibility(View.VISIBLE);
        fineDescription.setVisibility(View.VISIBLE);
        additionalText.setVisibility(View.VISIBLE);
        if (additionalText.getText() == getString(R.string.raserdelikt)) {
            additionalInformation.setVisibility(View.VISIBLE);
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
