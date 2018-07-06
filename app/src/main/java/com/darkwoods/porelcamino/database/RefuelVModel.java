package com.darkwoods.porelcamino.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class RefuelVModel extends ViewModel {

    private LiveData<List<Refuel>> refuelLiveData;


        public RefuelVModel(AppDatabase database,Integer journeyID) {
            refuelLiveData = database.refuelDao().loadRefuelsByJourneyId(journeyID);
        }


    public LiveData<List<Refuel>> getRefuelLiveData() {
        return refuelLiveData;
    }
}


