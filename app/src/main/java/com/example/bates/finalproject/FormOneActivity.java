package com.example.bates.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormOneActivity extends AppCompatActivity {

    private Context context;
    private Button backButton;
    private Button submitButton;
    private EditText dateField, sgField, degreesField, batchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        backButton = findViewById(R.id.form_back_button);
        submitButton = findViewById(R.id.submit_button);
        dateField = findViewById(R.id.fieldOne);
        sgField = findViewById(R.id.fieldTwo);
        degreesField = findViewById(R.id.fieldThree);
        batchField = findViewById(R.id.fieldFour);

        getButtonListener(backButton, submitButton);

/*            fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void getButtonListener(Button inBackButton, Button inSubmitButton) {
        final Button backButton = inBackButton;
        final Button submitButton = inSubmitButton;

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //logging in
                if (view.getId() == backButton.getId()) {
                    context = getApplicationContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        });


    }

}
