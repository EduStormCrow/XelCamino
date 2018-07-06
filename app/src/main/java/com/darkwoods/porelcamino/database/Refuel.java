package com.darkwoods.porelcamino.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Refuel {

    public static final String FUEL_PETROL_PREMIUM_UNLEAD = "PP";
    public static final String FUEL_PETROL_SUPER_UNLEAD = "PS";
    public static final String FUEL_DIESEL_PREMIUM = "DP";
    public static final String FUEL_DIESEL_SUPER_UNLEAD = "DS";
    public static final String FUEL_GAS = "GS";
    public static final String FUEL_BIOFUELS = "BI";
    public static final String FUEL_UNKNOWN = "XX";


    @PrimaryKey(autoGenerate = true)
    int id;
    private String fuelType; //One of the constants FUEL_
    private Double litres;
    private Date refuelDateTime;
    private Double priceXLt;
    private Double totalPrice;
    private Integer carKM;
    @ColumnInfo(name="journey_id")
    private Integer journeyId;
    private String place;

    public Refuel(int id, String fuelType, Double litres, Date refuelDateTime, Double priceXLt, Double totalPrice, Integer carKM, Integer journeyId,String place) {
        this.id = id;
        this.fuelType = fuelType;
        this.litres = litres;
        this.refuelDateTime = refuelDateTime;
        this.priceXLt = priceXLt;
        this.totalPrice = totalPrice;
        this.carKM = carKM;
        this.journeyId = journeyId;
        this.place = place;
    }
    @Ignore
    public Refuel(String fuelType, Double litres, Date refuelDateTime, Double priceXLt, Double totalPrice, Integer carKM, Integer journeyId,String place) {
        this.fuelType = fuelType;
        this.litres = litres;
        this.refuelDateTime = refuelDateTime;
        this.priceXLt = priceXLt;
        this.totalPrice = totalPrice;
        this.carKM = carKM;
        this.journeyId = journeyId;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Double getLitres() {
        return litres;
    }

    public void setLitres(Double litres) {
        this.litres = litres;
    }

    public Date getRefuelDateTime() {
        return refuelDateTime;
    }

    public void setRefuelDateTime(Date refuelDateTime) {
        this.refuelDateTime = refuelDateTime;
    }

    public Double getPriceXLt() {
        return priceXLt;
    }

    public void setPriceXLt(Double priceXLt) {
        this.priceXLt = priceXLt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCarKM() {
        return carKM;
    }

    public void setCarKM(Integer carKM) {
        this.carKM = carKM;
    }

    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }


}

