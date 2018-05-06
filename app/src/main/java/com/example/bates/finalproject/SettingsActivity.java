package com.example.bates.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SettingsActivity  extends AppCompatActivity {

    private Context context;
    private Button backButton;
    private String usernameString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        usernameString = bundle.getString("username");
        backButton = findViewById(R.id.settings_back_button);

/*            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void getButtonListener(Button inBackButton) {
        final Button backButton = inBackButton;

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


    }
}
