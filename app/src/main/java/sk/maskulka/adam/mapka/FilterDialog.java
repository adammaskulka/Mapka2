package sk.maskulka.adam.mapka;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by adam on 23.2.2017.
 */

public class FilterDialog extends DialogFragment {

    EditText datefrom;
    EditText dateto;
    Calendar myCalendarFrom;
    DatePickerDialog.OnDateSetListener dateFrom;
    String myFormat = "dd.MM.yyyy";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!isInLayout())
            return super.onCreateView(inflater, container, savedInstanceState);


        View view = inflater.inflate(R.layout.filter_dialog_layout, container);
        myCalendarFrom = Calendar.getInstance();

        datefrom = (EditText) view.findViewById(R.id.datefrom);
        dateto = (EditText) view.findViewById(R.id.dateto);

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        datefrom.setText(sdf.format(myCalendarFrom.getTime()));
        dateto.setText(sdf.format(myCalendarFrom.getTime()));


        dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarFrom.set(Calendar.YEAR, year);
                myCalendarFrom.set(Calendar.MONTH, monthOfYear);
                myCalendarFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd.MM.yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                datefrom.setText(sdf.format(myCalendarFrom.getTime()));
            }
        };

        datefrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(FilterDialog.this.getContext(), dateFrom, myCalendarFrom
                        .get(Calendar.YEAR), myCalendarFrom.get(Calendar.MONTH),
                        myCalendarFrom.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (isInLayout())
            return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(R.layout.filter_dialog_layout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO implement
                    }
                })
                .setNegativeButton("Zrušiť", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

}


