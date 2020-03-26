package com.felix.dtbs.models;

import java.util.Date;

public class Slot {
    private String DriverLicence;
    public Slot(){}

    public Slot(String driverLicence, Date slotDate, String slotTime) {
        DriverLicence = driverLicence;
        SlotDate = slotDate;
        SlotTime = slotTime;
    }

    public String getDriverLicence() {
        return DriverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        DriverLicence = driverLicence;
    }

    public Date getSlotDate() {
        return SlotDate;
    }

    public void setSlotDate(Date slotDate) {
        SlotDate = slotDate;
    }

    public String getSlotTime() {
        return SlotTime;
    }

    public void setSlotTime(String slotTime) {
        SlotTime = slotTime;
    }

    private Date SlotDate;
    private String SlotTime;

}
