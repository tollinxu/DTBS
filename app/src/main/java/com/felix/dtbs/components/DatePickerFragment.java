package com.felix.dtbs.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public DatePickerFragment(){
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE, 1);
        setMinDate(calendar.getTime());
        calendar.add(calendar.DATE, 6);
        setMaxDate(calendar.getTime());
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    private Date minDate;
    private Date maxDate;
    private Date selectedDate;

    public DialogInterface.OnClickListener getPositiveClickListner() {
        return positiveClickListner;
    }

    public void setPositiveClickListner(DialogInterface.OnClickListener positiveClickListner) {
        this.positiveClickListner = positiveClickListner;
    }

    private DialogInterface.OnClickListener positiveClickListner;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog =  new DatePickerDialog(getActivity(), this, year, month, day);
        if(this.getMinDate() != null){
            dialog.getDatePicker().setMinDate(this.getMinDate().getTime());
        }

        if(this.getMaxDate() != null){
            dialog.getDatePicker().setMaxDate(this.getMaxDate().getTime());
        }

        if(getPositiveClickListner() != null){
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", getPositiveClickListner());
        }
        return  dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        setSelectedDate(new Date(year, month, dayOfMonth));
    }
}
