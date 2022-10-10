package com.example.stopoholic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stopoholic.databinding.ActivityMainBinding;
import com.example.stopoholic.ui.main.MainFragment;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements GoalDialog.GoalDialogListener{

    private Button recordButton;
    private Button viewButton;
    private Button setGoalButton;
    private TextView todayIntakeTextView;
    private TextView goalTextView;
    MyDatabaseHelper db;
    private int progress = 0;
    private double todayIntake = 0;
    private int goalIntake;
    private ProgressBar progressBar;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String GOAL = "goal";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = (Button) findViewById(R.id.recordBtn);
        viewButton = (Button) findViewById(R.id.viewBtn);
        setGoalButton = (Button) findViewById(R.id.setGoalBtn);
        todayIntakeTextView = (TextView) findViewById(R.id.todayIntakeTextView);
        progressBar = findViewById(R.id.circleProgressBar);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecordActivity();
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewActivity();
            }
        });

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        db = new MyDatabaseHelper(MainActivity.this);





    }

    @Override
    protected void onResume() {
        super.onResume();

        DecimalFormat df = new DecimalFormat("0.0");
        todayIntake = Double.parseDouble(df.format(checkTodayIntake()));


        loadGoal();
        updateProgress();






        todayIntakeTextView.setText(Double.toString(todayIntake) + " / " + Integer.toString(goalIntake));
        //goalTextView.setText(Integer.toString(goalIntake));

    }

    public void openDialog() {
        GoalDialog goalDialog = new GoalDialog();
        goalDialog.show(getSupportFragmentManager(), "goal dialog");
    }

    @Override
    public void applyTexts(String goal) {
        saveGoalAsPref(goal);
        onResume();
    }

    public void openRecordActivity(){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);

    }

    public void openViewActivity(){
        Intent intent = new Intent(this, ViewRecordsActivity.class);
        startActivity(intent);

    }

    public double checkTodayIntake(){

        double weight = 0;

        Cursor cursor = db.readTodayRecords();

        if(cursor.getCount() == 0){
            return weight;
        }else{
            while (cursor.moveToNext()){
                weight = weight + Double.parseDouble(cursor.getString(4));
            }
        }

        return weight;

    }

    public void updateProgress(){
        progress = (int) Math.round(todayIntake) * 100 / goalIntake;
        progressBar.setProgress(progress);

    }


    public void saveGoalAsPref(String goal) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(GOAL, Integer.parseInt(goal));

        editor.apply();

        Toast.makeText(this, "Goal Set", Toast.LENGTH_SHORT).show();
    }

    public void loadGoal() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        goalIntake = sharedPreferences.getInt(GOAL, 40);
    }
}