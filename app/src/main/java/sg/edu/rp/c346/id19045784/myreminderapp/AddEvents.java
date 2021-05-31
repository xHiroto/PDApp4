package sg.edu.rp.c346.id19045784.myreminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddEvents extends AppCompatActivity {
    EditText etTitle, etDesc, etDate, etStart, etEnd;
    Button btnAdd;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__events);

        //declare the edit text
        etTitle = findViewById(R.id.updateTextEventTitle);
        etDesc = findViewById(R.id.updateTextDescription);
        etDate = findViewById(R.id.updateTextDate);
        etStart = findViewById(R.id.updateTextStart);
        etEnd = findViewById(R.id.updateTextEnd);
        btnAdd = findViewById(R.id.buttonAdd);
        //declare a Calender instance
        Calendar c = Calendar.getInstance();

        //set the default value shown when activtiy starts
        etDate.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR));
        String aMpM = "AM";
        if(c.get(Calendar.HOUR_OF_DAY) >11)
        {
            aMpM = "PM";
        }

        //Make the 24 hour time format to 12 hour time format
        int currentHour;
        if(c.get(Calendar.HOUR_OF_DAY)>11)
        {
            currentHour = c.get(Calendar.HOUR_OF_DAY) - 12;
        }
        else
        {
            currentHour = c.get(Calendar.HOUR_OF_DAY);
        }
        etStart.setText(String.format("%02d:%02d%s",currentHour,c.get(Calendar.MINUTE),aMpM));
        etEnd.setText(String.format("%02d:%02d%s", (currentHour+1),c.get(Calendar.MINUTE),aMpM));


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

                        String aMpM = "AM";
                        if(hourOfDay >11)
                        {
                            aMpM = "PM";
                        }

                        //Make the 24 hour time format to 12 hour time format
                        int currentHour;
                        if(hourOfDay>12)
                        {
                            currentHour = hourOfDay - 12;
                        }
                        else
                        {
                            currentHour = hourOfDay;
                        }

                        etStart.setText(String.format("%02d:%02d%s",currentHour,minutes,aMpM));
                        if (currentHour == 12){
                            etEnd.setText(String.format("%02d:%02d%s", ((currentHour-12)+1),minutes,aMpM));
                        }
                        else {
                            etEnd.setText(String.format("%02d:%02d%s", (currentHour+1),minutes,aMpM));
                        }


                    }
                }, currentHour, currentMinute, false);

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

                        String aMpM = "AM";
                        if(hourOfDay >11)
                        {
                            aMpM = "PM";
                        }

                        //Make the 24 hour time format to 12 hour time format
                        int currentHour;
                        if(hourOfDay>12)
                        {
                            currentHour = hourOfDay - 12;
                        }
                        else
                        {
                            currentHour = hourOfDay;
                        }


                        etEnd.setText(String.format("%02d:%02d%s", currentHour,minutes,aMpM));

                    }
                }, currentHour, currentMinute, false);

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
                if (!etTitle.getText().toString().isEmpty() & !etDesc.getText().toString().isEmpty()) {
                    //Code to dismiss the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etTitle.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etDesc.getWindowToken(), 0);


                    //prepare to pass data back to the MainActivty
                    String eventTitle = etTitle.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();
                    String date = etDate.getText().toString();
                    String timeStart = etStart.getText().toString().trim();
                    String timeEnd = etEnd.getText().toString().trim();



                    DBHelper dbh = new DBHelper(AddEvents.this);
                    long inserted_reminder = dbh.insertNote(eventTitle, desc, date, timeStart, timeEnd);
                    dbh.close();
                    Intent i = new Intent();
                    setResult(RESULT_OK, i);


                    String[] testdateSplit = date.split("/");
                    String nameOfMonth = "";
                    if (testdateSplit[1].equals("1")){
                        nameOfMonth = "January";
                    }
                    else if (testdateSplit[1].equals("2")){
                        nameOfMonth = "February";
                    }
                    else if (testdateSplit[1].equals("3")){
                        nameOfMonth = "March";
                    }
                    else if (testdateSplit[1].equals("4")){
                        nameOfMonth = "April";
                    }
                    else if (testdateSplit[1].equals("5")){
                        nameOfMonth = "May";
                    }
                    else if (testdateSplit[1].equals("6")){
                        nameOfMonth = "June";
                    }
                    else if (testdateSplit[1].equals("7")){
                        nameOfMonth = "July";
                    }
                    else if (testdateSplit[1].equals("8")){
                        nameOfMonth = "August";
                    }
                    else if (testdateSplit[1].equals("9")){
                        nameOfMonth = "September";
                    }
                    else if (testdateSplit[1].equals("10")){
                        nameOfMonth = "October";
                    }
                    else if (testdateSplit[1].equals("11")){
                        nameOfMonth = "November";
                    }
                    else {
                        nameOfMonth = "December";
                    }


                    DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy, h:mma");
                    String dateStarts = nameOfMonth + " " + testdateSplit[0] + ", " + testdateSplit[2] + ", " + timeStart;
                    String dateEnds = "June 31, 2021, 02:10PM";
                    ContentResolver cr = AddEvents.this.getContentResolver();
                    // event insert
                    ContentValues values = new ContentValues();
                    Date startDate = new Date(), endDate = new Date();
                    try {
                        startDate = formatter.parse(dateStarts);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        endDate = formatter.parse(dateEnds);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, eventTitle);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate.getTime());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, desc);
                   // intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    startActivity(intent);
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