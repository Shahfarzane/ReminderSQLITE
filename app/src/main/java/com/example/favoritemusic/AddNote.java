package com.example.favoritemusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class AddNote extends AppCompatActivity {

    TextView textView;
    Switch urgentSwitch;
    Switch catSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        catSwitch = findViewById(R.id.catSwitch);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        textView = findViewById(R.id.textView);

    }

    public void addNote(View view){
        int urgent;
        if(urgentSwitch.isChecked())
            urgent = 1;
        else urgent = 0;

        int category;
        if (catSwitch.isChecked())
            category = 2;
        else category =1;
        DBHelper db = new DBHelper(this);
        db.addNoteToDB(textView.getText().toString(),urgent,category);


        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

}
