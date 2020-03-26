package com.felix.dtbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CommonUtil {
    public static List<String> getTimeSlots(){
        List<String> slots = new ArrayList<>();
        for (int i = 9; i < 17 ; i++) {
            slots.add(String.valueOf(i) + ":00");
        }
        return slots;
    }

    public static List<String> getAvaliableDates(){
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("dd/MM/yyyy");// a为am/pm的标记
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        List<String> dates = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            dates.add(sdf.format(date));
        }
        return dates;
    }

    public static final  String Home = "Home";
    public static final  String BOOK_SLOT = "Book a slot";
    public static final  String VIEW_BY_DRIVER = "View slots by driver";
    public static final  String VIEW_BY_DAY = "View slots by day";

    public static final  int MAX_SLOT = 10;

    public static String[] getMenu() {
        return new String[]{Home, BOOK_SLOT, VIEW_BY_DRIVER, VIEW_BY_DAY};
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static HashMap<String, Object> getSlotItem(String driverLicense, Date slotDate, String slotTime) {
        HashMap<String, Object> slot = new HashMap<>();
        slot.put("tvDriverLicence", driverLicense);
        slot.put("tvSlotDate", dateFormat.format(slotDate));
        slot.put("tvSlotTime", slotTime);

        return slot;
    }

    public static boolean isWeekend(Calendar calendar) {
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        }

        return false;
    }
}
