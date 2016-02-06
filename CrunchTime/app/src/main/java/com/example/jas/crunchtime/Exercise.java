package com.example.jas.crunchtime;

/** Represents a type of exercise that a user can keep track of
 *  Created by Jas on 2/4/2016.
 */
public class Exercise {

    // Instance Variables
    private String _name;        // What one calls this exercise
    private double _convRate;     // The amount of unit needed to burn 1 Calorie
    private String _unit;        // The unit used to measure this exercise. 'minutes' or 'reps'

    // Constants
    public static final String min = "minutes";
    public static final String reps = "reps";

    // Constructor
    public Exercise(String name, int rate, String units){
        _name = name;
        _convRate = rate / 100.0; //rate is the amount needed to burn 100 Calories
        _unit = units;
    }

    // Accessors
    public String getName(){
        return this._name;
    }

    // Units: this.getUnit/Calorie
    public double getConversionRate(){
        return this._convRate;
    }

    public String getUnit(){
        return this._unit;
    }

}
