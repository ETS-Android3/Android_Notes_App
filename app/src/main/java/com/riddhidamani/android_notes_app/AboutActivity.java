package com.riddhidamani.android_notes_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Objects;

// About Activity Class
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_about);
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(this, "The Back button was pressed - Going to Main Activity!", Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}