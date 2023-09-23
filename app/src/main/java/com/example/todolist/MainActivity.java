package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private EditText editText;
    private Button btnNew;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Task> items = new ArrayList<>();
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnNew = findViewById(R.id.btnNew);
        recyclerView = findViewById(R.id.rvTasks);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        items = getTasks();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(items, this);
        recyclerView.setAdapter(adapter);

        btnNew.setOnClickListener(v -> {
            String newItem = editText.getText().toString();

            if (!newItem.isEmpty()) {
                ContentValues values = new ContentValues();
                values.put("taskName", newItem);
                long newRowId = db.insert("tasks", null, values);
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            editText.setText("");
        });
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String[] projection = {"taskID", "taskName"};
        Cursor cursor = db.query("tasks", projection, null, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int taskId = cursor.getInt(cursor.getColumnIndex("taskID"));
            @SuppressLint("Range") String taskName = cursor.getString(cursor.getColumnIndex("taskName"));
            Task task = new Task(taskId, taskName);
            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }

}