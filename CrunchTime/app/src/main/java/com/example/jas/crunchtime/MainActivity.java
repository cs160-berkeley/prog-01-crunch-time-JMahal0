package com.example.jas.crunchtime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Exercise> exercises;
    private List<String> exNames;
    private double weightFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exercises = new ArrayList<Exercise>();
        exercises.add(new Exercise("Pushup", 350, Exercise.reps));
        exercises.add(new Exercise("Situp", 200, Exercise.reps));
        exercises.add(new Exercise("Squats", 225, Exercise.reps));
        exercises.add(new Exercise("Leg-lift", 25, Exercise.min));
        exercises.add(new Exercise("Plank", 25, Exercise.min));
        exercises.add(new Exercise("Jumping Jacks", 10, Exercise.min));
        exercises.add(new Exercise("Pullup", 100, Exercise.reps));
        exercises.add(new Exercise("Cycling", 12, Exercise.min));
        exercises.add(new Exercise("Walking", 20, Exercise.min));
        exercises.add(new Exercise("Jogging", 12, Exercise.min));
        exercises.add(new Exercise("Swimming", 13, Exercise.min));
        exercises.add(new Exercise("Stair-Climbing", 15, Exercise.min));

        exNames = new ArrayList<String>();
        for (Exercise ex : exercises){
            exNames.add(ex.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sInput = (Spinner) findViewById(R.id.spinner1);
        sInput.setAdapter(adapter);

        sInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String exInput = "";
                for (Exercise ex : exercises) {
                    if (ex.getName().equals(sInput.getSelectedItem().toString())) {
                        exInput = ex.getUnit();
                    }
                }
                ((TextView) findViewById(R.id.txtUnit1)).setText(exInput);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                ((TextView) findViewById(R.id.txtUnit1)).setText(Exercise.reps);
            }
        });

        weightFactor = 1;
        final EditText wInput = (EditText) findViewById(R.id.weightInput);
        wInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                if (c.length() > 0){
                    double weight =  Double.parseDouble(c.toString());
                    weightFactor = weight/150;
                } else {
                    weightFactor = 0;
                }
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {

            }

            public void afterTextChanged(Editable c) {

            }
        });

    }

    /** Gets called when btnEx2Cal is clicked */
    public void updateCalorieCount(View view){
        double calories = 0;
        String in = ((EditText) findViewById(R.id.numInput1)).getText().toString();
        final Spinner sInput = (Spinner) findViewById(R.id.spinner1);

        // Find the selected exercise from spinner 1 and find the number of calories burnt
        for (Exercise ex : exercises) {
            if (ex.getName().equals(sInput.getSelectedItem().toString())) {
                calories = weightFactor * Double.parseDouble(in)/ ex.getConversionRate() ;
            }
        }

        if (calories == 1) {
            ((EditText) findViewById(R.id.calorieInput)).setText("1");
        } else if (calories == 0) {
            ((EditText) findViewById(R.id.calorieInput)).setText("0");
        } else {
            ((EditText) findViewById(R.id.calorieInput)).setText(String.format("%.1f" , calories));
        }
        calcAllExercises(calories/weightFactor);
    }

    public void updateExerciseCount(View view){
        double cals = Double.parseDouble(((EditText) findViewById(R.id.calorieInput)).getText().toString()) / weightFactor;
        calcAllExercises(cals);

        final Spinner sInput = (Spinner) findViewById(R.id.spinner1);

        // Find the selected exercise from spinner 1 and find the number of calories burnt
        for (Exercise ex : exercises) {
            if (ex.getName().equals(sInput.getSelectedItem().toString())) {
                ((EditText) findViewById(R.id.numInput1)).setText(String.format("%.1f", cals * ex.getConversionRate()));
            }
        }

    }

    // Calculate the amount of exercise from the number of calories burnt for each exercise
    private void calcAllExercises(double calories){
        List<String> otherExercisesInfo = new ArrayList<String>();
        for (Exercise ex : exercises) {
            String val = String.format("%.1f" , calories * ex.getConversionRate());
            otherExercisesInfo.add("Is equal to " + val + " " + ex.getUnit() + " of " + ex.getName());
        }

        final Spinner spin2 = (Spinner) findViewById(R.id.spinner2);
        spin2.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, otherExercisesInfo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapter);
    }


}
