package com.darkwoods.porelcamino.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class RefuelVModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final Integer journeyID;


     public RefuelVModelFactory(AppDatabase database, Integer journeyId)
     {
        mDb = database;
        journeyID = journeyId;

    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new RefuelVModel(mDb, journeyID);
    }
}
