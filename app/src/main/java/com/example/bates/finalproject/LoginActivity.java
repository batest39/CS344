package com.example.bates.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {

    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,}$";
    private static final String USERNAME_PATTERN = "([\\w\\.\\-_]+)?\\w+@[\\w-_]+(\\.\\w+){1,}";



    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private CheckBox mCheckBox;
    private View mProgressView;
    private View mLoginFormView;
    private Context context;
    private DbHelper mdbh;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mdbh = new DbHelper(getApplicationContext());
        db = mdbh.getWritableDatabase();

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);

        mCheckBox = (CheckBox) findViewById(R.id.registering_checkbox);
        context = getApplicationContext();

        getCheckboxListener(mSignInButton, mCheckBox);
        getLoginListener(mUsernameView, mPasswordView, mCheckBox, mSignInButton);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    public List<String> getUser(){
        SQLiteDatabase db = mdbh.getReadableDatabase();
        List<String> data = new ArrayList<>();

        String[] projection = {
                DbContract.User.COLUMN_NAME_USERNAME,
                DbContract.User.COLUMN_NAME_PASSWORD
        };

        String selection = DbContract.User.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { mUsernameView.getText().toString() };

        Cursor cursor = db.query(
                DbContract.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        while(cursor.moveToNext()){
            data.add(cursor.getString(1));
        }

        cursor.close();

        return data;
    }

    public List<String> getAllUsernames(){
        SQLiteDatabase db = mdbh.getReadableDatabase();
        List<String> data = new ArrayList<>();

        String[] projection = {
                DbContract.User.COLUMN_NAME_USERNAME
        };

        String selection = DbContract.User.COLUMN_NAME_USERNAME + " = ?";
        String[] selectionArgs = { mUsernameView.getText().toString() };

        Cursor cursor = db.query(
                DbContract.User.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        while(cursor.moveToNext()){
            data.add(cursor.getString(cursor.getColumnIndex(DbContract.User.COLUMN_NAME_USERNAME)));
        }

        cursor.close();

        return data;
    }

    private boolean isUsernameValid(String username) {
        boolean isValid = false;

        Pattern p = Pattern.compile(USERNAME_PATTERN);
        Matcher m = p.matcher(username);

        if (m.matches()){
/*            Toast toast = Toast.makeText(context,
                    "Valid password!", Toast.LENGTH_SHORT);
            toast.show();*/
            isValid = true;
        }

        return isValid;
    }

    private boolean isPasswordValid(String password) {
        boolean isValid = false;

        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(password);

        if (m.matches()){
/*            Toast toast = Toast.makeText(context,
                    "Valid password!", Toast.LENGTH_SHORT);
            toast.show();*/
            isValid = true;
        }

        return isValid;
    }

    public void getCheckboxListener(Button inButton, CheckBox inCheckBox){
        final Button loginButton = inButton;
        final CheckBox checkBox = inCheckBox;

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.getId() == checkBox.getId() && checkBox.isChecked()){
                    loginButton.setText("Register");
                } else {
                    loginButton.setText("Sign In");
                }
            }
        });
    }

    public void getLoginListener(EditText inUsername, EditText inPassword,
                                 CheckBox inCheckBox, Button inButton){
        final EditText username = inUsername;
        final EditText password = inPassword;
        final CheckBox checkBox = inCheckBox;
        final Button loginButton = inButton;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameString = username.getText().toString();
                String passwordString = password.getText().toString();
                //logging in
                if(view.getId() == loginButton.getId() && !checkBox.isChecked()){
                    List<String> users = getUser();
                    String x = users.get(0);
                    if (users.size() == 0)
                    {
                        Toast toast = Toast.makeText(context,
                                "This username is not in the database.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if(users.get(0).equals(passwordString)){
                        //show MainActivity
                        Intent intent = new Intent(context, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", usernameString);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(context,
                                "Incorrect username/password", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    //registering
                } else {
                    List<String> usernames = getAllUsernames();
                    if (!isUsernameValid(username.getText().toString())){
                        Toast toast = Toast.makeText(context,
                                "Username must be at least four characters long.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (!isPasswordValid(password.getText().toString())){
                        Toast toast = Toast.makeText(context,
                                "Password does not meet requirements.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (usernames.contains(username.getText().toString())) {
                        Toast toast = Toast.makeText(context,
                                "Username already exists.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        ContentValues values = new ContentValues();
                        values.put(DbContract.User.COLUMN_NAME_USERNAME, username.getText().toString());
                        values.put(DbContract.User.COLUMN_NAME_PASSWORD, password.getText().toString());

                        long insertedRowId = db.insert(DbContract.User.TABLE_NAME, null, values);

                        Toast toast = Toast.makeText(context,
                                "Success! Your account was created.", Toast.LENGTH_SHORT);
                        toast.show();

                        //show MainActivity
                        Intent intent = new Intent(context, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", usernameString);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}

