package com.darkwoods.porelcamino;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.darkwoods.porelcamino.database.AppDatabase;
import com.darkwoods.porelcamino.database.AppExecutors;
import com.darkwoods.porelcamino.database.CurrentJourneyVModel;
import com.darkwoods.porelcamino.database.CurrentJourneyVModelFactory;
import com.darkwoods.porelcamino.database.Journey;
import com.darkwoods.porelcamino.database.Refuel;
import com.darkwoods.porelcamino.utils.ConstantPreferences;
import com.darkwoods.porelcamino.utils.TextValidator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RefuelActivity extends AppCompatActivity {

    TextView mActualJourneyTxVw;
    EditText mPlaceTxVw;
    Spinner mFuelTypeSpinner;
    EditText mLitersTxVw;
    EditText mPriceLitreTxVw;
    EditText mTotalPriceTxVw;
    EditText mActualKmTxVw;
    TextView mDateTxVw;
    Integer  mJourneyId;

    Date mRefuelDateTime;
    String mFuelType;
    Button mSaveButton;
    Button mAddJourneyButton;
    AppDatabase mDb;


    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refuel);

        mDb= AppDatabase.getInstance(this.getApplication());
        //TODO check if in bundle activity ID
        initViews();


    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        //setting journey to invisible till we load the actual journey
        mActualJourneyTxVw = (TextView) findViewById(R.id.mActiveJourney);
        //mActualJourneyTxVw.setVisibility(View.INVISIBLE);

        //Get Current Journey
        CurrentJourneyVModelFactory factory = new CurrentJourneyVModelFactory(mDb);
        final CurrentJourneyVModel viewModel = ViewModelProviders.of(this, factory).get(CurrentJourneyVModel.class);


        viewModel.getCurrentJourney().observe(this, new Observer<Journey>() {
                    @Override
                    public void onChanged(@Nullable Journey data) {
                        populateJourneyUI(data);
                    }
            });
        //text
        mActualKmTxVw = (EditText) findViewById(R.id.mKm);
        mLitersTxVw = (EditText) findViewById(R.id.mlitre);
        mPriceLitreTxVw = (EditText) findViewById(R.id.mPriceLitre);
        mTotalPriceTxVw = (EditText) findViewById(R.id.mTotalPrice);
        mPlaceTxVw = (EditText) findViewById(R.id.mPlace);

        //Spinner for fuel types
        mFuelTypeSpinner = (Spinner) findViewById(R.id.spinnerFuelType);
        setupSpinner();

        // Date is shown but is not editabled
        mDateTxVw = (TextView) findViewById(R.id.mDate);
        mRefuelDateTime = new Date();
        String dateTime = dateFormat.format(mRefuelDateTime);
        mDateTxVw.setText(dateTime);

        //buttons
        mSaveButton = (Button) findViewById(R.id.buttonAddRefuel);
        mAddJourneyButton = (Button) findViewById(R.id.buttonAddJourney);





        mLitersTxVw.addTextChangedListener(new TextValidator(mLitersTxVw) {
            @Override public void validate(TextView textView, String text) {
                /* Insert your validation rules here */
                if (isValidBasicDataInt(mLitersTxVw)){
                    if (Integer.getInteger(text) > (Integer.getInteger(ConstantPreferences.TANKS_SIZE)))
                    mLitersTxVw.setError("Liters must be lower than your tanks size (50)" );

                }

            }
        });



        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        mAddJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(RefuelActivity.this, AddJourneyActivity.class);
                startActivity(myIntent);
            }
        });

    }



    public boolean isValidBasicDataInt(EditText txt) {
        String errorMsg = "";
        String s = txt.getText().toString();

        if (s.length() == 0) {
            errorMsg = "field cannot be empty please enter the correct values";
        }

        Integer value = null;

        try {
            value = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            errorMsg = "Please enter a valid number";
        }

        if (!errorMsg.isEmpty()) {
            txt.setError(errorMsg);
            return false;
        }else
        {
            return true;
        }

    }




    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter fuelTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_fuel_type, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        fuelTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mFuelTypeSpinner.setAdapter(fuelTypeSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mFuelTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {

                    if (selection.equals(getString(R.string.fuel_petrol_premium_unlead))) {
                    mFuelType = Refuel.FUEL_PETROL_PREMIUM_UNLEAD;
                    } else if (selection.equals(getString(R.string.fuel_petrol_super_unlead))) {
                    mFuelType = Refuel.FUEL_PETROL_SUPER_UNLEAD;
                    } else if (selection.equals(getString(R.string.fuel_diesel_premium))) {
                    mFuelType = Refuel.FUEL_DIESEL_PREMIUM;
                    } else if (selection.equals(getString(R.string.fuel_diesel_super_unlead))) {
                    mFuelType = Refuel.FUEL_DIESEL_SUPER_UNLEAD;
                    } else if (selection.equals(getString(R.string.fuel_gas))) {
                    mFuelType = Refuel.FUEL_GAS;
                    } else if (selection.equals(getString(R.string.fuel_biofuels))) {
                    mFuelType = Refuel.FUEL_BIOFUELS;
                    } else {
                    mFuelType =  Refuel.FUEL_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFuelType =  Refuel.FUEL_UNKNOWN;
            }
        });




    }


    /**
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {

        Boolean noErrors = false;

        //basic validations
        if (isValidBasicDataInt(mLitersTxVw) || isValidBasicDataInt(mPriceLitreTxVw) || isValidBasicDataInt(mTotalPriceTxVw)) {
           noErrors = true;
        }

        //business validations
        //TODO VALIDATE INPUT


        // if everything went smoothly
        if (noErrors){
        Double litres = Double.valueOf(mLitersTxVw.getText().toString());
        Double priceXLt = Double.valueOf(mPriceLitreTxVw.getText().toString());
        Double totalPrice = Double.valueOf(mTotalPriceTxVw.getText().toString());
        Integer carKM  = Integer.valueOf( mActualKmTxVw.getText().toString());
        String place = mPlaceTxVw.getText().toString();

        final Refuel refuel = new Refuel( mFuelType,  litres,  mRefuelDateTime,  priceXLt,  totalPrice,  carKM,  mJourneyId, place);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.refuelDao().insertRefuel(refuel);
                    finish();
                }
            });
        }



    }


//    //validate input data before save
//    private boolean  validateRefuel(Double litres, Double priceXLt,Double totalPrice, Integer carKM, String place){
//
//    return true;
//    }


    private void populateJourneyUI(Journey currentJourney) {
        if (currentJourney != null) {


            if (currentJourney.getName() != null) {
                mActualJourneyTxVw.setText(currentJourney.getName());
                //mActualJourneyTxVw.setVisibility(View.VISIBLE);
                mJourneyId = currentJourney.getId();
                mAddJourneyButton.setEnabled(false);
            }

        }else {
            // if no journey is active you can add one
            mAddJourneyButton.setEnabled(true);
        }

    }

}

