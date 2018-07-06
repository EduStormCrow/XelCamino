package com.darkwoods.porelcamino.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JourneyDao {
    @Query("SELECT * FROM Journey ORDER BY startKM")
    LiveData<List<Journey>> loadAllJourneys();

       @Insert
    void insertJourney(Journey journey);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateJourney(Journey journey);


    @Delete
    void deleteJourney(Journey journey);

    @Query("SELECT * FROM Journey WHERE id = :id")
    LiveData<Journey> loadJourneyById(int id);

    @Query("SELECT * FROM Journey ORDER BY startedAt DESC LIMIT 1")
    LiveData<Journey> getCurrentJourney();



}


