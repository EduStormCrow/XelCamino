package com.darkwoods.porelcamino.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Journey {
    @PrimaryKey(autoGenerate = true)
    private int  id;
    private Integer startKM = 0;
    private Integer endKM = 0;
    private Integer fuelTank = 0; //porcentage defining how much fuel was at the start of the activity_journey
    private Date startedAt;
    private Date finishedAt;
    private String name;



    @Ignore
    public Journey(Integer startKM, Integer fuelTank, Date startedAt, String name) {
        this.startKM = startKM;
        this.fuelTank = fuelTank;
        this.startedAt = startedAt;
        this.name =name;
    }

    public Journey(int id, Integer startKM, Integer endKM, Integer fuelTank, Date startedAt, Date finishedAt, String name) {
        this.id = id;
        this.startKM = startKM;
        this.endKM = endKM;
        this.fuelTank = fuelTank;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.name =name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStartKM() {
        return startKM;
    }

    public void setStartKM(Integer startKM) {
        this.startKM = startKM;
    }

    public Integer getEndKM() {
        return endKM;
    }

    public void setEndKM(Integer endKM) {
        this.endKM = endKM;
    }

    public Integer getFuelTank() {
        return fuelTank;
    }

    public void setFuelTank(Integer fuelTank) {
        this.fuelTank = fuelTank;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }
}

