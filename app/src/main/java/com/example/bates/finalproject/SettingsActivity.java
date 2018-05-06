package com.example.bates.finalproject;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class SettingsActivity  extends AppCompatActivity {

    private Context context, notificationContext;
    private EditText date;
    private EditText time;
    private Button backButton, submitButton;
    private String usernameString;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int year, month, day, hour, min;
    private int calYear, calMonth, calDay, calHour, calMin;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //createNotificationChannel();
        notificationContext = getApplicationContext();
        alarmManager = (AlarmManager) notificationContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(notificationContext, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(notificationContext, 0, intent, 0);



        Bundle bundle = getIntent().getExtras();
        usernameString = bundle.getString("username");
        date = findViewById(R.id.reminderDate);
        time = findViewById(R.id.reminderTime);
        backButton = findViewById(R.id.settings_back_button);
        submitButton = findViewById(R.id.settingsSubmitButton);


        createPickerDialogs();
        getButtonListener(backButton, submitButton, date, time);
    }


    public void createPickerDialogs(){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        calYear = year;
                        calMonth = monthOfYear + 1;
                        calDay = dayOfMonth;
                    }
                }, year, month, day);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time.setText( selectedHour + ":" + selectedMinute);
                calHour = selectedHour;
                calMin = selectedMinute;
            }
        }, hour, min, true);
    }

    public void getButtonListener(Button inBackButton, Button inSubmitButton, EditText inDate, EditText inTime) {
        final Button backButton = inBackButton;
        final Button submitButton = inSubmitButton;
        final EditText date = inDate;
        final EditText time = inTime;

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == date.getId()) {
                    datePickerDialog.show();
                }
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == time.getId()) {
                    timePickerDialog.show();
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == backButton.getId()) {
                    context = getApplicationContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameString);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == submitButton.getId()) {

                    int yearToAdd = calYear - Calendar.getInstance().get(Calendar.YEAR);
                    int monthToAdd = calMonth - 1 - Calendar.getInstance().get(Calendar.MONTH);
                    int dayToAdd = calDay - Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    int hourToAdd = calHour - Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    int minToAdd = calMin - Calendar.getInstance().get(Calendar.MINUTE);

                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.YEAR, yearToAdd);
                    c.add(Calendar.MONTH, monthToAdd);
                    c.add(Calendar.DAY_OF_MONTH, dayToAdd);
                    c.add(Calendar.HOUR_OF_DAY, hourToAdd);
                    c.add(Calendar.MINUTE, minToAdd);

                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), alarmIntent);

                    context = getApplicationContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameString);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
