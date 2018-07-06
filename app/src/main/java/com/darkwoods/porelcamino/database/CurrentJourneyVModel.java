package com.darkwoods.porelcamino.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class CurrentJourneyVModel extends ViewModel {

    private LiveData<Journey> JourneyLiveData;


        public CurrentJourneyVModel(AppDatabase database) {
            JourneyLiveData = database.journeyDao().getCurrentJourney();
        }

    public LiveData<Journey> getCurrentJourney() {
        return JourneyLiveData;
    }
}
