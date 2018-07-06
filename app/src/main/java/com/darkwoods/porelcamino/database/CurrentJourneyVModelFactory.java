package com.darkwoods.porelcamino.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class CurrentJourneyVModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;


     public CurrentJourneyVModelFactory(AppDatabase database) {
        mDb = database;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new CurrentJourneyVModel(mDb);
    }
}
