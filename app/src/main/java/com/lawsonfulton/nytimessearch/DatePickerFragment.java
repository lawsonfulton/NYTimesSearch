package com.lawsonfulton.nytimessearch;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public class MyDatePickerDialog extends DatePickerDialog {
        private Calendar calendar;

        // Regular constructor
        public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            calendar = Calendar.getInstance();
        }

        // Short constructor
        public MyDatePickerDialog(Context context, OnDateSetListener callBack, Calendar calendar) {
            super(context, callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            this.calendar = calendar;
        }

        @Override
        public void onDateChanged(DatePicker view, int year, int month, int day) {
            super.onDateChanged(view, year, month, day);
            calendar.set(year, month, day);
        }
    }

    private MyDatePickerDialog mDatePickerDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDatePickerDialog = new MyDatePickerDialog(getContext(), this, Calendar.getInstance());
        return mDatePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        SettingsDialog settingsDialog = (SettingsDialog) getTargetFragment();
        settingsDialog.setDate(year, month, day);
    }
}
