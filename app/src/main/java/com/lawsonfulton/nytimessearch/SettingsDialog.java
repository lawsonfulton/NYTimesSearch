package com.lawsonfulton.nytimessearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsDialog extends DialogFragment {

    public static interface OnCompleteListener {
        public abstract void onDialogComplete(SearchSettings newSettings);
    }

    private OnCompleteListener mListener;

    EditText etDate;
    String settingsDate;
    String prettyDate;

    public SettingsDialog() {
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    public static SettingsDialog newInstance(String title) {
        SettingsDialog frag = new SettingsDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        settingsDate = "";
        etDate = (EditText) view.findViewById(R.id.etDate);
        final CheckBox cbArts = (CheckBox) view.findViewById(R.id.cbArts);
        final CheckBox cbFashion = (CheckBox) view.findViewById(R.id.cbFashion);
        final CheckBox cbSports = (CheckBox) view.findViewById(R.id.cbSports);
        final Spinner dropdown = (Spinner)view.findViewById(R.id.sSortOrder);


        String[] items = new String[]{"Relevance", "Newest", "Oldest"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        //REstore old setttings
        SearchSettings oldSettings = (SearchSettings)getArguments().getSerializable("settings");
        etDate.setText(oldSettings.getPrettyDate());
        dropdown.setSelection(Arrays.asList(items).indexOf(oldSettings.getOrder()));

        if (oldSettings.newsDesks.contains("Arts")) {
            cbArts.setChecked(true);
        }
        if (oldSettings.newsDesks.contains("Fashion & Style")) {
            cbFashion.setChecked(true);
        }
        if (oldSettings.newsDesks.contains("Sports")) {
            cbSports.setChecked(true);
        }

        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order = dropdown.getSelectedItem().toString();
                ArrayList<String> newsDesks = new ArrayList<String>();

                if (cbArts.isChecked()) {
                    newsDesks.add("Arts");
                }
                if (cbFashion.isChecked()) {
                    newsDesks.add("Fashion & Style");
                }
                if (cbSports.isChecked()) {
                    newsDesks.add("Sports");
                }

                SearchSettings settings = new SearchSettings(settingsDate, prettyDate, order, newsDesks);
                mListener.onDialogComplete(settings);
                dismiss();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(SettingsDialog.this, 300);
                newFragment.show(getFragmentManager(), "date_picker_fragment");
            }
        });
    }

    public void setDate(int year, int month, int day) {
        String strYear = Integer.toString(year);
        String strMonth = String.format("%02d", month + 1);
        String strDay = String.format("%02d", day);

        settingsDate = strYear + strMonth + strDay;
        prettyDate = strMonth + "/" + strDay + "/" + strYear;
        etDate.setText(prettyDate);

    }
}
