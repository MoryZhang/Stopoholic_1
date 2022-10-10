package com.example.stopoholic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewRecordsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyDatabaseHelper db;
    ArrayList<String> type, content, volume, weight, date;
    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        recyclerView = findViewById(R.id.recyclerView);

        db = new MyDatabaseHelper(ViewRecordsActivity.this);

        type = new ArrayList<>();
        content = new ArrayList<>();
        volume = new ArrayList<>();
        weight = new ArrayList<>();
        date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(ViewRecordsActivity.this, type, content, volume, weight, date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewRecordsActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = db.readAllRecords();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Record", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                type.add(cursor.getString(1));
                content.add(cursor.getString(2));
                volume.add(cursor.getString(3));
                weight.add(cursor.getString(4));
                date.add(cursor.getString(5));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.deleteAll){
            Toast.makeText(this, "Records Deleted", Toast.LENGTH_SHORT).show();
            MyDatabaseHelper db = new MyDatabaseHelper(this);
            db.deleteRecords();
            recreate();
        }
        return super.onOptionsItemSelected(item);
    }
}