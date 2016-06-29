package com.gymassistant;

/**
 * Created by KamilH on 2016-06-29.
 */
public class UnitConversions {
    private String lengthUnit, weightUnit;
    private double CM_TO_INCH = 2.54;
    private double INCH_TO_CM = 1 / CM_TO_INCH;

    private double KG_TO_LB = 2.2046;
    private double LB_TO_KG = 1 / KG_TO_LB;

    public UnitConversions(String lengthUnit, String weightUnit){
        this.lengthUnit = lengthUnit;
        this.weightUnit = weightUnit;
    }

    public UnitConversions(String weightUnit){
        this.weightUnit = weightUnit;
    }

    public double saveLengthConverter(double value){
        if(lengthUnit.matches("inch")){
            return INCH_TO_CM * value;
        } else {
            return value;
        }
    }

    public double retrieveLengthConverter(double value){
        if(lengthUnit.matches("inch")){
            return CM_TO_INCH * value;
        } else {
            return value;
        }
    }

    public double saveWeightConverter(double value){
        if(weightUnit.matches("lb")){
            return LB_TO_KG * value;
        } else {
            return value;
        }
    }

    public double retrieveWeightConverter(double value){
        if(weightUnit.matches("lb")){
            return KG_TO_LB * value;
        } else {
            return value;
        }
    }
}