package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Task> items;
    private Context context;
    DBHelper dbHelper;
    SQLiteDatabase db;
    public MyAdapter(List<Task> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position).getName();
        holder.editTextItem.setText(item);

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        holder.btnDelete.setOnClickListener( v ->{
            int rowsAffected = db.delete("tasks", "taskID = ?", new String[]{String.valueOf(items.get(position).getID())});

            items.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText editTextItem;
        Button btnDelete;
        public ViewHolder(View view) {
            super(view);
            editTextItem = view.findViewById(R.id.editTextItem);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }

    private void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}

