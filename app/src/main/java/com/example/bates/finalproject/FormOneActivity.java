package com.example.bates.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FormOneActivity extends AppCompatActivity {

    private Context context;
    private Button backButton;
    private Button submitButton;
    private EditText dateField, sgField, degreesField, batchField;
    private TextView title;
    private String[] stringArr;
    private String username;
    private String wineBool;

    private String dateString;
    private double sg;
    private int degrees;
    private int batch;



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
        title = findViewById(R.id.formTitle);

        Bundle bundle = getIntent().getExtras();
        stringArr = bundle.getString("stringArr").split(",");
        username = stringArr[0];
        wineBool = stringArr[1];

        //wineBool = bundle.getString("wineBool");
        if(wineBool.equals("true")){
            title.setText(R.string.wine);
        }

        getButtonListener(backButton, submitButton);

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
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == submitButton.getId()){
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                    dateString = dateField.getText().toString();
                    sg = Double.parseDouble(sgField.getText().toString());
                    degrees = Integer.parseInt(degreesField.getText().toString());
                    batch = Integer.parseInt(batchField.getText().toString());

                    FormDataClass data = new FormDataClass(username, dateString, sg, degrees, batch);

                    if(wineBool.equals("false")){
                        database.child("mead").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast toast = Toast.makeText(context,
                                        "Success! Your data was recorded.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast = Toast.makeText(context,
                                        "Error: Your data could not be added.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    } else{
                        database.child("wine").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast toast = Toast.makeText(context,
                                        "Success! Your data was recorded.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast toast = Toast.makeText(context,
                                        "Error: Your data could not be added.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });
                    }

                    clearForm();

                    context = getApplicationContext();
                    Intent intent = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }

    private void clearForm(){
        dateField.setText("");
        sgField.setText("");
        degreesField.setText("");
        batchField.setText("");
    }

}
