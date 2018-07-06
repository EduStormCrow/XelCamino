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
public interface RefuelDao {
    @Query("SELECT * FROM Refuel")
    LiveData<List<Refuel>> loadAllRefuels();

    @Insert
    void insertRefuel(Refuel refuel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRefuel(Refuel refuel);

    @Delete
    void deleteRefuel(Refuel refuel);

    @Query("SELECT * FROM Refuel WHERE id = :id")
    LiveData<Refuel> loadRefuelById(int id);

    @Query("SELECT * FROM Refuel WHERE journey_id = :id")
    LiveData<List<Refuel>> loadRefuelsByJourneyId(int id);
}


