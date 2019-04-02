package com.example.favoritemusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class Detail extends AppCompatActivity {

    TextView contentText;
    TextView titleText;
    Switch urgentSwitch;
    Todo todo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleText = findViewById(R.id.titleText);
        contentText = findViewById(R.id.contentText);
        urgentSwitch = findViewById(R.id.urgentSwitch);

        Intent intent = getIntent();

        long todoId = intent.getLongExtra("REMINDERID", -1);

        DBHelper dbHelper = new DBHelper(this);
        todo = dbHelper.getTodoById((int)todoId);

        titleText.setText(todo.getTodolistTitle());
        contentText.setText(todo.getTodolistContent());

    }

    public void pressedNoteDone(View view){
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.deleteRow(todo);
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

    public void pressedSaveChanges(View view){
        int urgent;
        if(urgentSwitch.isChecked())
            urgent = 1;
        else urgent = 0;

        todo.setTodolistUrgent(urgent);
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updatePrio(todo );

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);


    }


}