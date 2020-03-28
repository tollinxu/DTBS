package com.felix.dtbs.service;

import com.felix.dtbs.CommonUtil;
import com.felix.dtbs.models.Slot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singoten model
 */
public class BookSlotService {
    private static BookSlotService instance;
    private List<Slot> store = new ArrayList<>();

    private BookSlotService() {
    }

    public static BookSlotService getInstance() {
        if (instance == null) {
            instance = new BookSlotService();
        }
        return instance;
    }

    /**
     * try to book a slot according to licenceNumber, day and hour
     *
     * @param licenceNumber
     * @param day           in format of dd/MM/yyyy
     * @param hour          in forat of HH
     * @return
     */
    public boolean bookTimeslot(String licenceNumber, String day, int hour) {
        // hours check
        if (hour < 9 || hour > 16) {
            return false;
        }

        // full check
        long currentSlotQuantity = store.stream()
                .filter((slot) -> day.equals(CommonUtil.SimpleDateFormat.format(slot.getSlotDate())) && String.valueOf(hour).equals(slot.getSlotTime()))
                .count();
        if (currentSlotQuantity >= CommonUtil.MAX_SLOT) {
            return false;
        }
        // today booking check
        long todaySlotsQuantity = store.stream()
                .filter((slot) -> day.equals(CommonUtil.SimpleDateFormat.format(slot.getSlotDate())) && licenceNumber.equals(slot.getDriverLicence()))
                .count();
        if (todaySlotsQuantity > 0) {
            return false;
        }

        Slot slot = null;
        try {
            Date theDay = CommonUtil.SimpleDateFormat.parse(day);

            Date today = new Date();
            // cannot book slot before today
            if (theDay.before(today)) {
                return false;
            }
            // weekend day check
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(today);
            calendar.add(Calendar.DATE, 7);
            Date lastDay = calendar.getTime();
            if (theDay.after(lastDay)) {
                return false;
            }

            calendar.setTime(theDay);
            if (CommonUtil.isWeekend(calendar)) {
                return false;
            }
            slot = new Slot(licenceNumber, theDay, String.valueOf(hour));
        } catch (ParseException e) {
            return false;
        }
        store.add(slot);
        return true;
    }

    public List<Slot> getTimeslotBooking(String licenceNumber){
        return store.stream().filter((slot -> licenceNumber.equals(slot.getDriverLicence()))).collect(Collectors.toList());
    }

    public List<Slot> getSlots(String day){
        return store.stream().filter((slot -> day.equals(CommonUtil.SimpleDateFormat.format(slot.getSlotDate())))).collect(Collectors.toList());
    }

    /**
     * only use for test to reset test environment
     */
    public void resetStore() {
        store.clear();
    }
}
