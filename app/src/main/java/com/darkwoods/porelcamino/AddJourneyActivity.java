package com.darkwoods.porelcamino;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.darkwoods.porelcamino.database.AppDatabase;
import com.darkwoods.porelcamino.database.AppExecutors;
import com.darkwoods.porelcamino.database.Journey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddJourneyActivity  extends AppCompatActivity {

    EditText mJourneyNameTxVw;
    EditText mJourneyStartKmTxVw;
    EditText mJourneyFuelTankTxVw;
    TextView mJourneyDateTimeTxVw;
    Button mJourneySaveButton;
    AppDatabase mDb;
    Date startedAt;

    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";


    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_journey);

            mDb= AppDatabase.getInstance(this.getApplication());
            initViews();


        }

        /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mJourneyNameTxVw = (EditText) findViewById(R.id.journey_name);
        mJourneyStartKmTxVw = (EditText) findViewById(R.id.journey_start_km);
        mJourneyFuelTankTxVw = (EditText) findViewById(R.id.journey_fuel_tank);
        mJourneyDateTimeTxVw = (TextView) findViewById(R.id.journey_date_time);
        mJourneySaveButton = (Button) findViewById(R.id.saveJourneyButton);

        startedAt = new Date();
        String dateTime = dateFormat.format(startedAt);
        mJourneyDateTimeTxVw.setText(dateTime);


        mJourneySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }



    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String name = mJourneyNameTxVw.getText().toString();

         Integer startKM = Integer.valueOf( mJourneyStartKmTxVw.getText().toString());
         Integer fuelTank = Integer.valueOf( mJourneyFuelTankTxVw.getText().toString());; //porcentage defining how much fuel was at the start of the activity_journey



        final Journey journey = new Journey(startKM,fuelTank,startedAt,name);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.journeyDao().insertJourney(journey);
                finish();
            }
        });
    }


    }
