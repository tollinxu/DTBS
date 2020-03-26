package com.felix.dtbs;
import com.felix.dtbs.service.BookSlotService;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static org.junit.Assert.assertEquals;

public class BookSlotServiceTest {

    @Before
    public void resetStore(){
        BookSlotService instance = BookSlotService.getInstance();
        instance.resetStore();
    }

    @Test
    public void bookTimeslot_when_the_driver_has_book_one_slot_within_the_same_day_then_return_false() {
        BookSlotService instance = BookSlotService.getInstance();
        String tomorrow = getNextDayString(true);
        instance.bookTimeslot("abc", tomorrow, 9);

        boolean result = instance.bookTimeslot("abc", tomorrow, 12);

        assertEquals(false, result);
    }

    private static Date getNextDay(boolean isWorkDay){
        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        while (CommonUtil.isWeekend(calendar) == isWorkDay) {
            calendar.add(calendar.DATE, 1);
            tomorrow = calendar.getTime();
        }

        return  tomorrow;
    }

    private static String getNextDayString(boolean isWorkDay){
        Date tomorrow = getNextDay(isWorkDay);
        return CommonUtil.dateFormat.format(tomorrow);
    }

    @Test
    public void bookTimeslot_when_the_driver_books_one_slot_return_true() {
        BookSlotService instance = BookSlotService.getInstance();
        String dateString = getNextDayString(true);
        boolean result = instance.bookTimeslot("driver", dateString, 9);
        assertEquals(result, true);
    }

    @Test
    public void bookTimeslot_when_the_driver_books_one_slot_when_this_hour_is_full_return_false() {
        BookSlotService instance = BookSlotService.getInstance();
        String tomorrow = getNextDayString(true);
        for (int i = 0; i < CommonUtil.MAX_SLOT; i++) {
            instance.bookTimeslot(String.valueOf(i), tomorrow, 9);
        }

        boolean result = instance.bookTimeslot("drive", tomorrow, 9);
        assertEquals(false, result);
    }

    @Test
    public void bookTimeslot_when_the_driver_books_one_slot_in_weekend_return_false() {
        BookSlotService instance = BookSlotService.getInstance();
        String tomorrow = getNextDayString(false);
        boolean result = instance.bookTimeslot("1", tomorrow, 9);
        assertEquals(result, false);
    }

    @Test
    public void bookTimeslot_when_the_driver_books_one_slot_outof_next_7_days_return_false() {
        Calendar calendar = new GregorianCalendar();
        Date today = new Date();
        calendar.setTime(today);
        calendar.add(calendar.DATE, 8);
        Date tomorrow = calendar.getTime();
        while (CommonUtil.isWeekend(calendar)){
            calendar.add(calendar.DATE, 1);
            tomorrow = calendar.getTime();
        }
        BookSlotService instance = BookSlotService.getInstance();
        boolean result = instance.bookTimeslot("1", CommonUtil.dateFormat.format(tomorrow), 9);
        assertEquals(result, false);
    }

    @Test
    public void bookTimeslot_when_book_one_slot_when_date_formate_incorrect_return_false(){
        BookSlotService instance = BookSlotService.getInstance();
        Date tomorrow =  getNextDay(true);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean result = instance.bookTimeslot("1", format.format(tomorrow), 9);
        assertEquals(result, false);
    }

    @Test
    public void bookTimeslot_when_book_one_slot_when_hour_less_then_9_return_false(){
        BookSlotService instance = BookSlotService.getInstance();
        boolean result = instance.bookTimeslot("1", getNextDayString(true), 8);
        assertEquals(result, false);
    }

    @Test
    public void bookTimeslot_when_book_one_slot_when_hour_greater_then_16_return_false(){
        BookSlotService instance = BookSlotService.getInstance();
        boolean result = instance.bookTimeslot("1", getNextDayString(true), 17);
        assertEquals(result, false);
    }
}
