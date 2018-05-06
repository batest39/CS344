package com.example.bates.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button form1Button;
    private Button form2Button;
    private Button settingsButton;
    private Button historyButton;
    private Context context;
    private String usernameString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        usernameString = bundle.getString("username");

        form1Button = findViewById(R.id.formOneButton);
        form2Button = findViewById(R.id.formTwoButton);
        settingsButton = findViewById(R.id.settingsButton);
        historyButton = findViewById(R.id.historyButton);


        getButtonListener(form1Button, form2Button, settingsButton, historyButton);
    }


    public void getButtonListener(Button inForm1Button, Button inForm2Button,
                                  Button inSettingsButton, Button inHistoryButton) {
        final Button form1Button = inForm1Button;
        final Button form2Button = inForm2Button;
        final Button settingsButton = inSettingsButton;
        final Button historyButton = inHistoryButton;

        form1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == form1Button.getId()) {
                    context = getApplicationContext();

                    Bundle bundle = new Bundle();
                    bundle.putString("stringArr", usernameString + ",false");
                    Intent intent = new Intent(context, FormOneActivity.class);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        form2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == form2Button.getId()) {
                    context = getApplicationContext();
                    Intent intent = new Intent(context, FormOneActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stringArr", usernameString + ",true");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == settingsButton.getId()) {
                    context = getApplicationContext();
                    Intent intent = new Intent(context, SettingsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameString);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == historyButton.getId()) {
                    context = getApplicationContext();
                    Intent intent = new Intent(context, HistoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", usernameString);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
