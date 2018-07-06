package com.darkwoods.porelcamino;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.darkwoods.porelcamino.database.AppDatabase;
import com.darkwoods.porelcamino.database.AppExecutors;
import com.darkwoods.porelcamino.database.CurrentJourneyVModel;
import com.darkwoods.porelcamino.database.CurrentJourneyVModelFactory;
import com.darkwoods.porelcamino.database.Journey;
import com.darkwoods.porelcamino.database.Refuel;
import com.darkwoods.porelcamino.database.RefuelVModel;
import com.darkwoods.porelcamino.database.RefuelVModelFactory;

import java.util.Date;
import java.util.List;


import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class JourneyActivity  extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RefuelAdapter mAdapter;
    private Button mJourneyAdd;
    private Button mJourneyEnd;
    private Button mRefuel;

    AppDatabase mDb;
    Journey currentJourney;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);


        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        CurrentJourneyVModelFactory factory = new CurrentJourneyVModelFactory(mDb);
        final CurrentJourneyVModel viewModel = ViewModelProviders.of(this, factory).get(CurrentJourneyVModel.class);

        viewModel.getCurrentJourney().observe(this, new Observer<Journey>() {
        @Override
        public void onChanged(@Nullable Journey data) {
        populateUI(data);
        }

    });

    }


    private void populateUI(Journey currentJourney) {
        if (currentJourney != null) {
            if (currentJourney.getName() != null) {
                TextView journeyNameTxVw = (TextView) findViewById(R.id.tx_journey_name);
                journeyNameTxVw.setText(currentJourney.getName());
            }
            if (currentJourney.getStartKM() != null) {
                TextView journeyStartKMTxVw = (TextView) findViewById(R.id.tx_journey_start_km);
                journeyStartKMTxVw.setText(String.valueOf(currentJourney.getStartKM()));
            }

            mRecyclerView = findViewById(R.id.refuel_recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Initialize the adapter and attach it to the RecyclerView
            mAdapter = new RefuelAdapter(this, null);
            mRecyclerView.setAdapter(mAdapter);

            // TODO check if the decoration is working
            DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
            mRecyclerView.addItemDecoration(decoration);
            setupViewModel(currentJourney);
        }
    };




    private void setupViewModel(Journey currentJourney) {
        RefuelVModelFactory factory = new RefuelVModelFactory(mDb, currentJourney.getId());
        final RefuelVModel viewModel = ViewModelProviders.of(this,factory).get(RefuelVModel.class);
        viewModel.getRefuelLiveData().observe(this, new Observer<List<Refuel>>() {
            @Override
            public void onChanged(@Nullable List<Refuel> refuelEntries) {
                //Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setmRefuelEntries(refuelEntries);
            }
        });
    }



    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mJourneyAdd = (Button) findViewById(R.id.buttonAddJourney);
        mJourneyEnd = (Button) findViewById(R.id.buttonEndJourney);
        mRefuel = (Button) findViewById(R.id.buttonRefuel);

        mJourneyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(JourneyActivity.this, AddJourneyActivity.class);
                startActivity(myIntent);
            }
        });

        mJourneyEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEndJourneyButtonClicked();
            }
        });


        mRefuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(JourneyActivity.this, RefuelActivity.class);
                startActivity(myIntent);
            }
        });

    }



    /**
     * It ends the current journey by setting today as the end date
     */

    public void onEndJourneyButtonClicked() {
    //TODO: se deberian  pedir km finales?

        //Double check that the user wants to end the journey
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("End Journey??")
                .setMessage("Are you sure you want to end the current journey?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentJourney.setFinishedAt(new Date());
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.journeyDao().updateJourney(currentJourney);
                                finish();
                            }
                        });
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }
}
