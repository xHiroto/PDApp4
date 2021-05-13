package sg.edu.rp.c346.id19045784.myreminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEvents extends AppCompatActivity {
    EditText etTitle, etDesc,etDate, etStart, etEnd;
    Button btnAdd;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__events);

        //declare the edit text
        etTitle = findViewById(R.id.editTextEventTitle);
        etDesc = findViewById(R.id.editTextDescription);
        etDate = findViewById(R.id.editTextDate);
        etStart = findViewById(R.id.editTextStart);
        etEnd = findViewById(R.id.editTextEnd);
        btnAdd = findViewById(R.id.buttonAdd);
        //declare a Calender instance
        Calendar c = Calendar.getInstance();

        //set the default value shown when activtiy starts
        etDate.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR));
        etStart.setText(String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
        c.add(Calendar.HOUR_OF_DAY, 1);
        etEnd.setText(String.format("%02d:%02d",c.get(Calendar.HOUR_OF_DAY ), c.get(Calendar.MINUTE)));


        //when user presses the
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                //Code to dismiss the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);

                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddEvents.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                etDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        //when user press the start time text time picker will pop up
        etStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                //Code to dismiss the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);


                timePickerDialog = new TimePickerDialog(AddEvents.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {

                            etStart.setText(String.format("%02d:%02d", hourOfDay, minutes));
                            etEnd.setText((String.format("%02d:%02d", hourOfDay + 1, minutes)));


                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }

        });

        //when user press the end time text time picker will pop up
        etEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);

                //Code to dismiss the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);

                timePickerDialog = new TimePickerDialog(AddEvents.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {


                        etEnd.setText((String.format("%02d:%02d", hourOfDay, minutes)));

                    }
                }, currentHour, currentMinute, true);

                timePickerDialog.show();
            }
        });

        //when user presses the button to save
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = -1;
                //condition check for empty fields
                //if not empty
                if(!etTitle.getText().toString().isEmpty() & !etDesc.getText().toString().isEmpty()) {
                    //Code to dismiss the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);


                    //prepare to pass data back to the MainActivty
                    String eventTitle = etTitle.getText().toString();
                    String desc = etDesc.getText().toString();
                    String date = etDate.getText().toString();
                    String timeStart = etStart.getText().toString();
                    String timeEnd = etEnd.getText().toString();
                    Intent i = new Intent();
                    i.putExtra("id", id +1);
                    i.putExtra("event", eventTitle);
                    i.putExtra("desc", desc);
                    i.putExtra("date", date);
                    i.putExtra("timeStart", timeStart);
                    i.putExtra("timeEnd", timeEnd);
                    setResult(RESULT_OK, i);
                    finish();
                }
                //if empty
                else {
                    //Code to dismiss the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);
                    Toast.makeText(AddEvents.this, "Both Fields cannot be Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}